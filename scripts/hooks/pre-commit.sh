#!/bin/bash

#
# Runs our lint checks on the files which are going to be committed.
#

if [[ "$OSTYPE" == "darwin"* ]]; then
  SCRIPT_DIR="$(dirname "$(perl -MCwd -e "print Cwd::abs_path shift" "${BASH_SOURCE[0]}")")"
else
  SCRIPT_DIR="$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"
fi
source "$SCRIPT_DIR/../common/common.sh"
ROOT_DIR="$SCRIPT_DIR/../.."

P_TAG="pre-commit hook"

main() {
  local bash_files=()
  local kt_files=()
  local abs_file_path

  while read -r -d $'\0' relative_file_path; do
    abs_file_path="$ROOT_DIR/$relative_file_path"
    case "$relative_file_path" in
    *.sh) bash_files+=("$abs_file_path") ;;
    *.kt) kt_files+=("$abs_file_path") ;;
    esac
  done < <(
    # Filters files by extensions for perf reasons.
    # Filters only added, modified and renamed files.
    git diff --cached --name-only -z --diff-filter=AMR -- "*.sh" "*.kt"
  )

  [[ ${#bash_files[@]} -gt 0 ]] && run_bash_linter "${bash_files[@]}"
  [[ ${#kt_files[@]} -gt 0 ]] && run_kotlin_linter "${kt_files[@]}"
  exit 0
}

run_bash_linter() {
  if ! command -v shellcheck >/dev/null 2>&1; then
    print_error "$P_TAG" "you must have 'shellcheck' installed to lint bash files"
    exit 1
  fi

  if ! shellcheck -x "$@"; then
    print_error "$P_TAG" "bash lint checks failed, please check the errors and commit again"
    exit 1
  fi
  print_success "$P_TAG" "bash lint checks successful"
}

run_kotlin_linter() {
  local detekt_dir="$ROOT_DIR/detekt"
  local detekt_bin_dir="$detekt_dir/bin"
  local detekt_cli_jar="$detekt_bin_dir/detekt_cli.jar"
  local detekt_formatting_jar="$detekt_bin_dir/detekt_formatting.jar"
  local detekt_twitter_compose_jar="$detekt_bin_dir/detekt_twitter_compose.jar"
  if [[ ! -f "$detekt_cli_jar" || ! -f "$detekt_formatting_jar" || ! -f "$detekt_twitter_compose_jar" ]]; then
    print_error "$P_TAG" "detekt jars in 'detekt/bin' required to verify Kotlin files, have you run './scripts/tuner.sh'?"
    exit 1
  fi
  kotlin_files_input=$(arr_join_to_str "$@")
  java -jar "$detekt_cli_jar" \
    --plugins "$detekt_twitter_compose_jar,$detekt_formatting_jar" \
    --config "$detekt_dir/config.yml" \
    --jvm-target "1.8" \
    --input "$kotlin_files_input" || {
    print_error "$P_TAG" "kotlin lint checks failed, please check the errors and commit again"
    exit 1
  }
  print_success "$P_TAG" "kotlin lint checks successful"
}

main

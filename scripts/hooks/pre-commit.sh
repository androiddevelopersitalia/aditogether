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
  done < <(git diff --cached --name-only -z -- "*.sh" "*.kt") # Filters files for perf reasons.

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
  : # TODO run kotlin linter
}

main

#!/bin/bash

#
# Git commit-msg hook which enforces our commit convention.
# More info can be found in `scripts/common/commit-msg-regex.sh`.
#

if [[ "$OSTYPE" == "darwin"* ]]; then
  SCRIPT_DIR="$(dirname "$(perl -MCwd -e "print Cwd::abs_path shift" "${BASH_SOURCE[0]}")")"
else
  SCRIPT_DIR="$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"
fi
source "$SCRIPT_DIR/../common/common.sh"
source "$SCRIPT_DIR/../common/commit-msg-regex.sh"

P_TAG="commit-msg hook"

COMMIT_MSG_FILE=$1

main() {
  if [[ -z "$COMMIT_MSG_REGEX" ]]; then
    print_error "$P_TAG" "COMMIT_MSG_REGEX env var missing"
    exit 1
  fi

  local lines_count
  lines_count=$(grep -c ^ "$COMMIT_MSG_FILE")
  if [[ $lines_count -gt 1 ]]; then
    print_error "$P_TAG" "the commit message should be max 1 line"
    exit 1
  fi

  if ! grep -q -E "$COMMIT_MSG_REGEX" "$COMMIT_MSG_FILE"; then
    print_error "$P_TAG" "the commit message does not follow https://www.conventionalcommits.org/"
    exit 1
  fi

  print_success "$P_TAG" "the commit message is valid"
}

main

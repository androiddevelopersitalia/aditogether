#!/bin/bash

#
# Verifies that the commits of our repo are following our commit convention.
# More info can be found in `scripts/common/commit-msg-regex.sh`.
# This script is designed to be used by our CI.
#

SCRIPT_DIR="$(dirname "${BASH_SOURCE[0]}")"
source "$SCRIPT_DIR/../common/common.sh"
source "$SCRIPT_DIR/../common/commit-msg-regex.sh"

P_TAG="validate-commit-messages"

main() {
  if [[ -z "$COMMIT_MSG_REGEX" ]]; then
      print_error $P_TAG "COMMIT_MSG_REGEX env var missing"
      exit 1
  fi

  local invalid_commits
  invalid_commits=$(git log --grep="$COMMIT_MSG_REGEX" -E --invert-grep --pretty="format:%s")
  print_info $P_TAG "validating commit messages"
  if [[ -z "$invalid_commits" ]]; then
      print_success $P_TAG "all commit messages are valid"
      exit 0
  fi
  print_error $P_TAG "the following commits are not valid"
  echo "════════════════════════════════"
  echo "$invalid_commits"
  echo "════════════════════════════════"
  exit 1
}

main

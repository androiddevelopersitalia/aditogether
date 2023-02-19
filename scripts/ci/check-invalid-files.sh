#!/bin/bash

#
# Checks that the repo does not contains ome forbidden files (e.g. *.gradle.kts files).
# This script is designed to be used by our CI.
#

SCRIPT_DIR="$(dirname "${BASH_SOURCE[0]}")"
source "$SCRIPT_DIR/../common/common.sh"

P_TAG="check-invalid-files"

main() {
  print_info $P_TAG "checking if *.gradle.kts files are present"
  local gradle_kts_files
  gradle_kts_files=$(git ls-files "*.gradle.kts")
  if [[ -z "$gradle_kts_files" ]]; then
    print_success $P_TAG "no *.gradle.kts files found"
    exit 0
  fi
  print_error $P_TAG "the following *.gradle.kts files are forbidden, please convert them to *.gradle files"
  echo "════════════════════════════════"
  echo "$gradle_kts_files"
  echo "════════════════════════════════"
  exit 1
}

main

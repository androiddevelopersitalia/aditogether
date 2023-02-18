#!/bin/bash

# Exposes the regex used to validate the Git commit message so it can be shared between scripts.
# This regex is based on https://www.conventionalcommits.org/
#
# Examples of valid commit messages:
# - chore: this is the message
# - feat(news): this is the message
# - refactor(news)!: this is the message
#
# Examples on invalid commit messages:
# - chore:
# - fix(): this is the message
# - docs: This is the message
# - random_keyword: this is the message
export COMMIT_MSG_REGEX="^(build|chore|ci|docs|feat|fix|perf|refactor|style|test)+(\([a-z0-9\-_\/]+\))?!?:\s[a-z][a-zA-Z0-9 -_\/\`]+$"

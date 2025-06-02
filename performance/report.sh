#!/bin/bash
set -e

if [ -z "$1" ]; then
  echo "ERROR: You must specify a parameter in YYYYMMDD-HHMMSS format."
  echo "e.g.: $0 20250602-140540"
  exit 1
fi

date="$1"

if [[ ! "$date" =~ ^[0-9]{8}-[0-9]{6}$ ]]; then
  echo "ERROR: Invalid 'date' value."
  exit 1
fi

current_dir="$(pwd)"
results_dir="$current_dir/results/$date"

mkdir -p "$results_dir"
jmeter -g "$results_dir/view-result-tree.csv" -o "$results_dir/report"
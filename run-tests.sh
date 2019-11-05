#! /bin/bash

echo "Installing docstring-checker locally..."

lein install

cd test-project

echo "Checking profile 'good'..."
lein with-profile +good docstring-checker
if [ $? -ne 0 ]; then
    echo "Failure in profile 'good': Should have passed"
    exit -1
fi

echo "Checking profile 'good-with-patterns'..."
lein with-profile +good-with-patterns docstring-checker
if [ $? -ne 0 ]; then
    echo "Failure in profile 'good-with-patterns': Should have passed"
    exit -1
fi

echo "Checking profile 'bad'..."
lein with-profile +bad docstring-checker
if [ $? -eq 0 ]; then
    echo "Failure in profile 'bad': Should have failed"
    exit -1
fi
echo "(This was supposed to fail. Things are working correctly.)"

echo "Checking profile 'bad-with-patterns'..."
lein with-profile +bad-with-patterns docstring-checker
if [ $? -eq 0 ]; then
    echo "Failure in profile 'bad-with-patterns': Should have failed"
    exit -1
fi
echo "(This was supposed to fail. Things are working correctly.)"

echo "All tests passed."

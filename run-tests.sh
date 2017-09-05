#! /bin/bash

cd test-projects

echo "Checking project 'good'..."
cd good && lein docstring-checker
if [ $? -ne 0 ]; then
    echo "Failure in project 'good': Should have passed"
    exit -1
fi
cd ..

echo "Checking project 'good-with-patterns'..."
cd good-with-patterns && lein docstring-checker
if [ $? -ne 0 ]; then
    echo "Failure in project 'good-with-patterns': Should have passed"
    exit -1
fi
cd ..

echo "Checking project 'bad'..."
cd bad && lein docstring-checker
if [ $? -eq 0 ]; then
    echo "Failure in project 'bad': Should have failed"
    exit -1
fi
echo "(This was supposed to fail. Things are working correctly.)"
cd ..

echo "Checking project 'bad-with-patterns'..."
cd bad-with-patterns && lein docstring-checker
if [ $? -eq 0 ]; then
    echo "Failure in project 'bad-with-patterns': Should have failed"
    exit -1
fi
echo "(This was supposed to fail. Things are working correctly.)"
cd ..

echo "All tests passed."

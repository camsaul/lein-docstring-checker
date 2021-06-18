# NOTICE: AS OF JUNE 2021 THIS LINTER IS OFFICIALLY DEPRECATED!

**[`clj-kondo`](https://github.com/clj-kondo/clj-kondo)** now includes an optional [docstring checker linter](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md#missing-docstring), so there's no need for this little linter anymore. Use `clj-kondo` instead!

# docstring-checker

[![Clojars Project](https://clojars.org/docstring-checker/latest-version.svg)](http://clojars.org/docstring-checker)

[![CircleCI](https://circleci.com/gh/camsaul/lein-docstring-checker.svg?style=svg)](https://circleci.com/gh/camsaul/lein-docstring-checker)
[![GitHub license](https://img.shields.io/badge/license-3%E2%80%92Clause%20BSD-blue.svg)](https://raw.githubusercontent.com/camsaul/lein-docstring-checker/master/LICENSE.txt)

Leiningen Plugin that lints your Clojure project and checks that every public var is documented.

## Usage

Add `docstring-checker` to your `:plugins` in your `project.clj`:

```clojure
:plugins [[docstring-checker "1.1.0"]]
```

Run the linter:

```bash
$ lein docstring-checker
```

Optionally specify which namespace regex patterns to `:include` and `:exclude` in your `project.clj`. (By default, the linter will check everything in your `:source-paths`):

```clojure
:docstring-checker {:include [#"^my-project\.core"]
                    :exclude [#"test"
                              #"^my-project\.secret"]}
```

The linter will fail if any public vars in namespaces that match the specified patterns are undocumented.

## Running Tests

Docstring Checker has a small Leiningen project with a few different profiles in the `test-project` directory that is used to make sure things are working correctly.

You can run the shell script to test them:

```bash
./run-tests.sh
```

These tests also run on [CircleCI](https://circleci.com/gh/camsaul/lein-docstring-checker) whenever a new commit is pushed.


## License

Copyright © 2017-2021 Cam Saul

Distributed under the [3-Clause BSD License](https://raw.githubusercontent.com/camsaul/lein-docstring-checker/master/LICENSE.txt).

# docstring-checker

[![Clojars Project](https://clojars.org/docstring-checker/latest-version.svg)](http://clojars.org/docstring-checker)

[![CircleCI](https://circleci.com/gh/camsaul/lein-docstring-checker.svg?style=svg)](https://circleci.com/gh/camsaul/lein-docstring-checker)
[![GitHub license](https://img.shields.io/badge/license-3%E2%80%92Clause%20BSD-blue.svg)](https://raw.githubusercontent.com/camsaul/lein-docstring-checker/master/LICENSE.txt)

Leiningen Plugin that lints your Clojure project and checks that every public var is documented.

## Usage

Add `docstring-checker` to your `:plugins` in your `project.clj`:

```clojure
:plugins [[docstring-checker "1.0.3"]]
```

Optionally specify which namespace regex patterns to `:include` and `:exclude` in your `project.clj`:

```clojure
:docstring-checker {:include [#"^my-project\.core"]
                    :exclude [#"test"
                              #"^my-project\.secret"]}
```

Then run the linter with

```bash
$ lein docstring-checker
```

The linter will fail if any public vars in namespaces that match the specified patterns are undocumented.


## Running Tests

Docstring Checker has a few small Leiningen projects in the `test-projects` directory that are used to make sure things are working correctly.
You can run the shell script to test them:

```bash
./run-tests.sh
```

These tests run on [CircleCI](https://circleci.com/gh/camsaul/lein-docstring-checker) whenever a new commit is pushed.


## License

Copyright © 2018 Cam Saul

Distributed under the [3-Clause BSD License](https://raw.githubusercontent.com/camsaul/lein-docstring-checker/master/LICENSE.txt).

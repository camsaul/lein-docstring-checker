# docstring-checker

Leiningen Plugin that lints your Clojure project and checks that every public var is documented.

## Usage

Specify which namespace regex patterns to `:include` and `:exclude` in your `project.clj`:

```clojure
:docstring-checker {:include [#"^my-project\.core"]
                    :exclude [#"test"
                              #"^my-project\.secret
```

Then run the linter with

```bash
$ lein docstring-checker
```

## License

Copyright © 2016 Cam Saül

Distributed under the 3-Clause BSD License.

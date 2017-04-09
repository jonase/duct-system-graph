# duct-system-graph

A command line tool to visualize your [duct](https://github.com/duct-framework/duct) system.

## Usage

Make sure you have [graphviz](http://www.graphviz.org/) installed and download the [latest `duct-system-graph.jar`]()

```
java -jar duct-system-graph.jar path/to/system.edn | dot -Tsvg -o system.svg
```

`duct-system-graph` accepts multiple `system.edn` files as input and will [`meta-merge`](https://github.com/weavejester/meta-merge) them the same way as duct does:

```
java -jar duct-system-graph.jar path/to/system.edn path/to/dev/system.edn
```

## License

Copyright © 2017 Jonas Enlund

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

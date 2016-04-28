# bg-stats

`bg-stats` is a little calculator for Baldur's Gate Trilogy
ability points and bonuses based on in-game choices.

It's mainly intended as a small-but-not-quite trivial example of the
use of [scalajs-react](https://github.com/japgolly/scalajs-react),
[monifu](https://monix.io).

## Building

To get the fully optimized version, run

```
$ sbt fullOptJS
```

To get the development version, run

```
$ sbt fastOptJS
```

## Running

The program is entirely client-side, so you'll just need a HTTP
server to serve the root directory of the project.

Assuming you've got that up and running on port 8081, just open
one of the following links:

- [Optimized Version](http://localhost:8081/)
- [Development Version](http://localhost:8081/index-dev.html)

# License

Copyright 2016 Bardur Arantsson <bardur@scientician.net>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

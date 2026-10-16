# Agent Instructions

## Formatting

`.editorconfig` is the authority on formatting for every file in this repository. Read it before writing or editing a file, and apply every section
whose glob matches the file: `[*]` applies to everything, and a narrower section such as `[*.java]` or `[*.xml]` adds to it or overrides it, with
later sections winning over earlier ones. The properties of the matching sections decide:

- `max_line_length`: the column to wrap at. Do not wrap at a habitual width such as 80, 100 or 120. Tables, URLs and literals that cannot be broken
  are the only exceptions.
- `indent_style`: spaces or tab characters. Never mix them, and never convert one to the other in an unrelated edit.
- `indent_size` and `tab_width`: how wide one indentation level is, and how wide a tab character is.
- `end_of_line`, `charset`, `insert_final_newline` and `trim_trailing_whitespace`: line endings, encoding, the final newline and trailing whitespace.
- Editor-specific properties (`ij_*` for IntelliJ, such as the Java import layout): follow them too when writing the code they describe.

Where `.editorconfig` leaves a property unset for a file type, follow the convention of the files around it.

Where a skill, a reference page or a doc states a different width or indentation, `.editorconfig` wins; fix the conflicting text rather than
following it. A build-enforced setting (Checkstyle, the Eclipse formatter profile, impsort) must agree with `.editorconfig`; if the two disagree,
report it instead of choosing one.

Rewrap and reindent only the lines you change. Do not reformat existing text in an unrelated edit.

## Dockerfiles

A Dockerfile sits in its module's `src/main/docker/`. Everything copied into the image sits under `src/main/docker/container/`, rooted at `/`:
`container/usr/local/bin/tool` is `/usr/local/bin/tool` in the image. Scripts the build runs, and the data they use (patches, config, test sources),
go under `container/opt/container/<name>/`: the scripts in its `bin/`, the data beside it. `<name>` is a short name for the image: its name without
the project prefix (`build-support` for `<project>-build-support`).

- **Inline RUNs are for package installs and a little configuration only**: `apt-get` install and cleanup, a `sed` over one config file with a check
  that it took, a link, a `mkdir`. Anything that is really a script (a download and build, a `case` or a loop, steps that need comments to follow) is
  a script file in `bin/`, and the RUN is only the script's name. The first stage puts that `bin/` on `PATH`.
- **Scripts** are named `<subject>-<verb>.sh` (`openssl-build.sh`, `openssl-patch.sh`, `node-install.sh`), so they sort by what they act on. They run
  with `set -eu` (or `-eux`), read the stage's build args from the environment and check each one they need at the top (`: "${NAME:?}"`), and are
  executable in the repository. Below its license header (which the build adds), a script starts with a comment only when there is a reason to
  give that the code does not show.
- **Copy into each stage only the files it uses**, by their own paths, never `container/` as a whole: a stage's layer cache is keyed on what it
  copied, so copying everything would re-run a slow build whenever an unrelated file changed. Copy a file just before the RUN that uses it. Split a
  slow step from a fast one that changes more often into two scripts and two RUNs, with the fast one's files copied between them. The final stage
  copies only what the image's users need; build scripts stay in the stages that ran them.
- **Stages** are named for the product they build, in lower case (`openssl`, `node`), or for their role (`base`, `toolchain`); the final stage has no
  name. Each starts with a three-line comment block giving its human-readable name directly above its `FROM`:

  ```dockerfile
  #
  # OpenSSL
  #
  FROM toolchain AS openssl
  ```

- **Within a stage**, in this order, a blank line between each group: `FROM`; its `ARG`s, alphabetically; the package-install `RUN`; the `COPY`s, data
  before the script; the `RUN` of the script.
- **ENV** lists its variables alphabetically in one instruction, and moves a variable whose value uses another into a second `ENV` after it.
- **No other comments in the Dockerfile**: the license header the build adds and the stage headers are the only comments. A reason a step needs
  goes in its script.

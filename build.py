#!/usr/bin/python
import argparse
import build.git as git
import os
import subprocess

def assert_no_changes():
    """Assert that there are no uncommitted changes."""
    if git.has_changes():
        print("ERROR: Project has local changes!")
        os.sys.exit(1)

def assert_branch_is(branch):
    """Assert that we're on the given branch."""
    if git.current_branch() != branch:
        print("ERROR: Must be on gh-pages branch!")
        os.sys.exit(1)

def sbt(args):
    """Run an 'sbt' command."""
    subprocess.run(["sbt"] + args, check = True)

def do_local(args):
    """Preform a local build for development."""
    # Tasks
    tasks = []
    tasks.append("fastOptJS")
    if args.full:
        tasks.append("fullOptJS")
    tasks.append("exportedProducts")
    # Build
    sbt(tasks)

def do_publish(args):
    # Preconditions
    assert_branch_is("gh-pages")
    assert_no_changes()
    # Perform a full, clean build.
    sbt(["clean", "fastOptJS", "fullOptJS", "exportedProducts"])
    # Add build normally-ignored files; we use --force because the files are in
    # ignored directories and are not present on the 'master' branch. Any files
    # which are not normally ignored must have already been committed because
    # of assert_no_changes()
    git.force_add([
        "target/scala-2.12/bg-stats-fastopt.js",
        "target/scala-2.12/bg-stats-fastopt.js.map",
        "target/scala-2.12/bg-stats-jsdeps.js",
        "target/scala-2.12/bg-stats-jsdeps.min.js",
        "target/scala-2.12/bg-stats-opt.js",
        "target/scala-2.12/bg-stats-opt.js.map",
        "target/web/sass/main/app.css",
        "target/web/sass/main/app.css.map",
    ])
    # Commit
    git.commit("Update GitHub Pages", allow_empty = True)
    # Push?
    if args.push:
        git.push("origin", force=True)

def main():
    # Arguments
    parser = argparse.ArgumentParser()
    subparsers = parser.add_subparsers(
        title = "Commands",
        help = "Run the specified task")

    # Parser for the "local" command
    parser_local = subparsers.add_parser("local")
    parser_local.add_argument(
        "--full",
        dest = "full",
        action = "store_true",
        help = "perform FULLY OPTIMIZED build too")
    parser_local.set_defaults(func = do_local)

    # Parser for the "publish" command
    parser_publish = subparsers.add_parser("publish")
    parser_publish.add_argument(
        "--push",
        dest = "push",
        action = "store_true",
        help = "push to origin")
    parser_publish.set_defaults(func = do_publish)

    # Run
    args = parser.parse_args()
    if "func" in args:
        args.func(args)
    else:
        parser.print_help()
        os.sys.exit(1)

main()

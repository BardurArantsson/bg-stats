from contextlib import contextmanager
from subprocess import run, PIPE

def has_changes():
    output = run(["git", "status", "--porcelain=2"], check = True, stdout=PIPE).stdout
    return len(output) > 0

def force_add(paths):
    run(["git", "add", "--force", "--"] + paths, check = True)

def push(remote, force = False):
    # Build
    cmd = ["git", "push"]
    if force:
        cmd.append("--force")
    cmd.append(remote)
    # Run
    run(cmd, check = True)

def commit(message, allow_empty = False):
    # Build
    cmd = ["git", "commit", "-m", message]
    if allow_empty:
        cmd.append("--allow-empty")
    # Run
    run(cmd, check = True)

def current_branch():
    return run(
        ["git", "symbolic-ref", "--short", "HEAD"],
        check = True,
        stdout=PIPE).stdout.decode().rstrip()

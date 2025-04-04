*** 8.1 ***
# f)
The project has passed (with 54% coverage) because SonarQube Quality Gate typically enforces thresholds for critical issues and my project likely had no critical failures .

# g)

Issue               Problem description                                                         How to solve

Security            (No issues)                                                                 ...

Reliability         (No issues)                                                                 ...

Maintainability     Unused method parameters should be removed                                  Remove this unused method parameter "subset".

Security hotspot    Using pseudorandom number generators (PRNGs) is security-sensitive          Use a cryptographically secure pseudo random number generator (CSPRNG) like "java.security.
SecureRandom" in place of a non-cryptographic PRNG. 


# h)
1. Checkstyle -> Enforces coding standards and style guidelines to keep code consistent across teams 

2. PMD -> Detects common programming flaws, identifies "dead code" and potential bugs early.

3. SpotBugs -> Focuses on runtime bugs and vulnerabilities by analyzing bytecodeand it catches security flaws and performance issues (like for example resource leaks).

*** 8.2 ***

# c) 
The analysis reveals 68% test coverage (38 untested lines), 24 maintainability issues (code smells increasing technical debt), and 1 reliability bug (a potential runtime error). While there’s no duplicated code, the security hotspots (marked "O/A") and suboptimal coverage suggest areas for improvement. 

# d)
Lines Not Covered: 38 lines are untested (out of 281 total) -> 68% line coverage.
Conditions are not explicitly shown, but typically lower than line coverage.
68% coverage is moderate—acceptable for prototypes for low for production (aim is normally ≥80%).
The 38 uncovered lines may hide critical bugs, especially in edge cases.
What to do? Improve coverage by targeting untested logic (e.g., error handling, complex branches).


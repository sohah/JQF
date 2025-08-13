<!--
This file is part of the SV-Benchmarks collection of verification tasks:
https://github.com/sosy-lab/sv-benchmarks

SPDX-FileCopyrightText: 2011-2020 The SV-Benchmarks Community

SPDX-License-Identifier: Apache-2.0
-->

# Java benchmarks

## Verification Task Structure

Verification tasks are grouped in directories depending on their source,
e.g., `jbmc-regression`.
Within these directories, each verification task consists of a YAML file
in the format defined by BenchExec for task-definition files.
These YAML files define the list of input files (Java sources) of a task
and the expected verdict for each possible property.

The programs are assumed to be written in Java 1.8.
All Java source files of a task need to have the suffix `.java`.
Program files must have a copyright header indicating
the source of the benchmark (at least in the "main" source file).
The program may call the Java standard library (`java.*`, `javax.*`).

## Properties

For each program at least one property file needs to be listed
in the task-definition file, which defines the entry point
and the property that the verifier should check.

For checking (un)reachability,
we use the `assert` keyword provided in the Java language:
The property `G assert` specifies that all `assert` statements
in the program can never fail.

A property file that defines the method `main` in the class `Main`
in the default package as the entry point,
and uses the assert property would look as follows:

    CHECK( init(Main.main()), LTL(G assert) )

Other properties are currently not defined.

## Compiling the Verification Tasks

The verification task need to be compilable by putting all `.java` files
in directories listed as input files on the sourcepath of a Java 8 compiler.

## Using the Verification Tasks with BenchExec

BenchExec will pass the paths
that are listed as input files in a task-definition file
to the tool-info module,
which can pass them to the verifier
or for example expand them to a list of single `.java` files,
depending on what the verifier needs.
If a verification tool requires `.class` files or a `.jar` file as input
it should use regular Java utilities to create these artifacts
(in a wrapper script if necessary).

## Rules for Nondeterminism

The only admissible source of nondeterminism are the return values of
the methods defined in the `org.sosy_lab.sv_benchmarks.Verifier`
class, provided in
`java/common/org/sosy_lab/sv_benchmarks/Verifier.java` in the
`sv-benchmarks` repository. In order to make the benchmarks
compilable, `../common/` needs to be added to the `input_files`
property of the benchmark's YAML file.

The methods in `org.sosy_lab.sv_benchmarks.Verifier` call methods
of the `java.util.Random` class. The rationale is to provide
straightforward compatibility with verifiers that implement a
nondeterministic semantics for the `java.util.Random` class, i.e.
the methods in `java.util.Random` are expected to return a
nondeterministic value instead of a random value, but satisfying
the same constraints on their value range.

`org.sosy_lab.sv_benchmarks.Verifier` also provides an `assume`
method, which is defined as `Runtime.getRuntime().halt(1)`.  It is
recommended to use `assume` or `return` (if in the entry point method)
to restrict the nondeterminism as they do not impact the termination
behavior of a program. For example, using `while(!condition); would
make any program with such assumptions be classified non-terminating
when a potential _Java Termination_ category might be introduced in
future.

Any library methods that make system calls are not allowed in
verification tasks.

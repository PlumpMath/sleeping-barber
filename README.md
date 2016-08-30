# Sleeping Barber Problem

When I first read about core.async I thought that it should be the right tool to solve some well-known synchronization problems with elegance.

This is my take on Dijkstra's Sleeping Barber Problem.

## Problem statement

Each customer, when he arrives, looks to see what the barber is doing. If the barber is sleeping, then the customer wakes him up and sits in the chair. If the barber is cutting hair, then the customer goes to the waiting room. If there is a free chair in the waiting room, the customer sits in it and waits his turn.

## Usage

`lein run` would run the code with the default settings.
`sleeping-barber.core-test` namespace ensures some common-sense assumptions are hold true.

## License

Copyright © 2016 Denis Korzunov

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

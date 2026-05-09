# Practice 3 - Adversarial Search: Minimax and Alpha-Beta Pruning

Course: Artificial Intelligence (Inteligencia Artificial)
Practice: Practice 3 - Adversarial Search
University: Universidad de Huelva
Year: 2025-2026

## 📄 Description

Adversarial search agent for a Tron-based maze game. Two players compete
to cross the maze from opposite sides, leaving a trail that becomes a
permanent wall. A player loses if they crash into a wall, their own trail,
the opponent's trail, or run out of valid moves.

Unlike Practice 2 (single-agent pathfinding with BFS/DFS/A*), here the
agent must plan assuming the opponent also plays optimally to make us lose.

## 🎯 Learning Objectives

- Understanding adversarial search vs single-agent search
- Implementing Minimax with limited depth following lecture pseudocode
- Designing heuristic evaluation functions for non-terminal states
- Implementing Alpha-Beta pruning to reduce explored nodes
- Comparing performance across different search depths

## 🤖 Agents Implemented

- **BusquedaMinimax:** Minimax algorithm with depth limit 4. Three separate
  functions following slide 41 pseudocode: DECISION-MINIMAX (pensar),
  MAX-VALUE (maxValor), MIN-VALUE (minValor)
- **BusquedaAlfaBeta:** Alpha-Beta pruning optimization of Minimax with
  depth limit 6. Same result as Minimax but exploring up to 64% fewer nodes
  at depth 6
- **AgenteDummy:** Random agent that picks a valid move each turn (opponent)

## 🧠 Heuristic Evaluation

Two heuristics implemented in a separate modular `Heuristica` class:

- **utilidad** (complete): `mobilityDiff * 10 - manhattanDist * 2`
  Prioritizes having more free moves than the opponent
- **utilidadSimple** (simple): `-manhattanDist * 2`
  Only considers distance to goal, ignores mobility

Mobility has higher weight (x10) because in Tron surviving is more
important than advancing quickly.

## 📊 Results

| Algorithm | Depth | Nodes/turn (min/max) | Result vs Dummy |
|-----------|-------|----------------------|-----------------|
| Minimax   | 2     | 4 / 12               | ✅ Win (10 cycles) |
| Minimax   | 4     | 2 / 92               | ✅ Win (23 cycles) |
| Minimax   | 6     | 2 / 614              | ✅ Win (10 cycles) |
| AlfaBeta  | 2     | 4 / 12               | ✅ Win (13 cycles) |
| AlfaBeta  | 4     | 2 / 73               | ✅ Win (13 cycles) |
| AlfaBeta  | 6     | 2 / 222              | ✅ Win (15 cycles) |

Alpha-Beta reduces max nodes by **64%** at depth 6 vs Minimax.

## 🗺️ Maps Available

- **mapaTexto:** Standard open map 16x7 — used for all benchmarks
- **MAPA_CALLEJON:** Small corridor map 9x6
- **MAPA_GRANDE:** Large maze 20x10

## 💻 Technologies Used

- **Language:** Java
- **IDE:** Eclipse
- **Package:** P3

## 🚀 How to Run

1. Clone the repository
2. Open in Eclipse
3. Run `Tron_P3_Base.java`
4. Change agents in `main()` to test different combinations:

```java
// AlfaBeta vs Dummy
BusquedaAlfaBeta agenteA = new BusquedaAlfaBeta();
AgenteDummy agenteB = new AgenteDummy();

// Minimax vs Dummy
BusquedaMinimax agenteA = new BusquedaMinimax();
AgenteDummy agenteB = new AgenteDummy();

// AlfaBeta vs Minimax
BusquedaAlfaBeta agenteA = new BusquedaAlfaBeta();
BusquedaMinimax agenteB = new BusquedaMinimax();
```

## 📚 Builds On

- Practice 1: Tron navigation agents (reactive + greedy)
- Practice 2: Uninformed search (BFS, DFS, A*)

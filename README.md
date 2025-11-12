# UrgencyQueue (Java)

A custom priority queue implementation in Java that orders elements by urgency using linked nodes and generics.

## Overview
This project implements an **Urgency Queue Abstract Data Type (ADT)** entirely from scratch.  
It behaves like a priority queue, always dequeuing the **most urgent** element based on natural ordering or a custom comparator.

This project demonstrates:
- Generic programming (`<T extends Comparable<T>>`)
- Linked-node data structures
- Priority logic + stable ordering
- Interface-based design
- Error handling and exceptions

---

## Key Features
- **Generic type support**  
- **Linked-node internal structure** (no built-in PriorityQueue used)
- **Stable priority ordering** for equal-urgency items  
- **Core operations:**
  - `enqueue(T item)`
  - `dequeue()`
  - `peek()`
  - `size()`
  - `toString()`
- **Exception handling:**  
  Throws `IllegalStateException` on `peek()` or `dequeue()` when empty.

# jsonata4java-benchmark

Minimal Benchmarking of [IBM Jsonata4Java](https://github.com/IBM/JSONata4Java) vs doing the same
work in native java.

This benchmark is implemented
using [Java Microbenchmark Harness (JMH)](https://github.com/openjdk/jmh)

## Latest Run (2023-12-11T20:00:49Z IST) Results

[![img.png](assets/2023-12-11T20:00:49Z.png)](https://github.com/Dhi13man/jsonata4java-benchmark/blob/218dd4403842fecb80a0713c1a80c83396089eb9/reports/2023-12-11T20%3A00%3A49Z/native_vs_jsonata_vs_jsonata_precompiled.txt)

### Key Findings

As per the benchmarking results,

1. logic implemented natively in Java is between 2 and 3 orders of magnitude faster than
   the same logic implemented in Jsonata.
2. The Jsonata precompiled implementation is up to 1 order of magnitude faster than the
   compiled-at-runtime Jsonata implementation.
3. The Jsonata implementation code is much more repeatable and flexible than the native
   implementation.

### Summary

Despite Jsonata seeming much slower than native implementation, most systems are not going to be
doing this order of magnitude of processing anyway. So, the Jsonata implementation is a good choice
for most systems, because of its flexibility.
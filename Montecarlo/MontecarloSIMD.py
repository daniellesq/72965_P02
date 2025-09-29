import numpy as np
import time

def main():
    n = 10_000_000
    
    print("Creando arrays...")
    a = np.random.rand(n).astype(np.float32)
    b = np.random.rand(n).astype(np.float32)
    c = np.zeros(n, dtype=np.float32)

    # Escalar
    print("Operación escalar...")
    start = time.time()
    for i in range(n):
        c[i] = a[i] * b[i] + 2.0
    scalar_time = time.time() - start

    # SIMD
    print("Operación vectorial SIMD...")
    start = time.time()
    c_simd = a * b + 2.0
    simd_time = time.time() - start

    print(f"Tiempo escalar: {scalar_time:.4f}s")
    print(f"Tiempo SIMD:   {simd_time:.4f}s")
    print(f"Speedup: {scalar_time/simd_time:.2f}x")
    print(f"Iguales: {np.allclose(c, c_simd)}")

if __name__ == "__main__":
    main()

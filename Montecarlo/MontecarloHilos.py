import threading
import random

# Variable compartida
global_count = 0
lock = threading.Lock()

def monte_carlo_pi(num_samples, thread_id):
    global global_count
    local_count = 0
    for _ in range(num_samples):
        x = random.random()
        y = random.random()
        if x**2 + y**2 <= 1.0:
            local_count += 1

    # sección crítica
    with lock:
        print(f"Hilo {thread_id}: +{local_count} puntos")
        global_count += local_count

def main():
    total_samples = 1_000_000
    num_threads = 4
    samples_per_thread = total_samples // num_threads

    threads = []
    for i in range(num_threads):
        t = threading.Thread(target=monte_carlo_pi, args=(samples_per_thread, i))
        threads.append(t)
        t.start()

    for t in threads:
        t.join()

    pi_approx = (4 * global_count) / total_samples
    print(f"\n[Threads] Aproximación de π = {pi_approx}")
    print(f"Error = {abs(pi_approx - 3.1415926535)}")

if __name__ == "__main__":
    main()

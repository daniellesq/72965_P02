from mpi4py import MPI
import random

def main():
    comm = MPI.COMM_WORLD
    rank = comm.Get_rank()
    size = comm.Get_size()

    total_samples = 1_000_000
    samples_per_process = total_samples // size

    local_count = 0
    random.seed(rank)
    for _ in range(samples_per_process):
        x = random.random()
        y = random.random()
        if x**2 + y**2 <= 1.0:
            local_count += 1

    print(f"Proceso {rank}: {local_count} puntos")

    all_counts = comm.gather(local_count, root=0)

    if rank == 0:
        global_count = sum(all_counts)
        pi_approx = (4 * global_count) / total_samples
        print(f"\n[ MPI ] Aproximación de π = {pi_approx}")
        print(f"Error = {abs(pi_approx - 3.1415926535)}")

if __name__ == "__main__":
    main()

#include "cuda_helper.cuh"

void cudaVectorAdd(float *a, float *b, float *c, int numElements, int repetitions, bool warmup) {
	float hostToDevice = 0;
	float compute = 0;
	float deviceToHost = 0;
	clock_t start = clock();

	for (int i = 0; i < repetitions; i++)
	{
		// TODO: Implement a parallel vector addition on CUDA
	}

	float total = float(clock() - start) / (CLOCKS_PER_SEC * repetitions);

	if (!warmup)
	{
		printf("CUDA: %.3lf seconds\n", total);
		printf("CUDA: Copy input to device: %.3lf seconds\n", hostToDevice / (1000 * repetitions));
		printf("CUDA: Compute time: %.3lf seconds\n", compute / (1000 * repetitions));
		printf("CUDA: Copy output to host: %.3lf seconds\n", deviceToHost / (1000 * repetitions));
	}
}

void fillRandomArray(float *a, int numElements) {
	for (int i = 0; i < numElements; i++) {
		a[i] = rand() / (float)RAND_MAX;
	}
}

void verifyResults(float *a, float *b, float *c, int numElements) {
	for (int i = 0; i < numElements; i++) {
        if (fabs(a[i] + b[i] - c[i]) > 1e-5) {
            fprintf(stderr, "Result verification failed at element %d!\n", i);
            exit(EXIT_FAILURE);
        }
    }
}

void sequentialVectorAdd(float *a, float *b, float *c, int numElements) {
	clock_t start = clock();

	for (int i = 0; i < numElements; i++) {
		c[i] = a[i] + b[i];
	}

	float diff = float(clock() - start) / CLOCKS_PER_SEC;
	printf("Sequential: %.3lf seconds\n", diff);
}

int main() {
	int N = 10000000;
	size_t size = N * sizeof(float);

	float *h_a = (float *)malloc(size);
	handleAllocationError(h_a);
	fillRandomArray(h_a, N);
	
	float *h_b = (float *)malloc(size);
	handleAllocationError(h_b);
	fillRandomArray(h_b, N);
	
	float *h_c = (float *)malloc(size);
	handleAllocationError(h_c);

	cudaVectorAdd(h_a, h_b, h_c, N, 10, true);
	verifyResults(h_a, h_b, h_c, N);
	cudaVectorAdd(h_a, h_b, h_c, N, 100, false);

	sequentialVectorAdd(h_a, h_b, h_c, N);

	free(h_a);
	free(h_b);
	free(h_c);

	return 0;
}

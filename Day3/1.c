#include <stdio.h>
#include <stdlib.h>
#include <math.h>

void buildMatrix(int msize, int a[msize][msize], int size, int value);

int main(int argc, char **argv) {

    if (argc != 2) {
        printf("Usage %s input\n", argv[0]);
        exit(-1);
    }

    long input = strtol(argv[1], NULL, 10);
    double width_dbl = sqrt(input);
    int width = ceil(width_dbl);
    if (width % 2 == 0)
        width++;

    int matrix[width][width];

    buildMatrix(width, matrix, width, width*width);

    int targetR, targetC;
    for (int r = 0; r < width; ++r) {
        for (int c = 0; c < width; ++c) {
            //printf("%5d ", matrix[r][c]);
            if (matrix[r][c] == input) {
                targetR = r;
                targetC = c;
            }

        }
        //printf("\n");
    }

    int centerR = width/2;
    int centerC = width/2;

    double distance = (fabs(centerR-targetR)) + (fabs(centerC-targetC));

    printf("Shortest distance: %.0lf\n", distance);
}

void buildMatrix(int msize, int a[msize][msize], int size, int value) {
    int row, col;
    if (size < 1)
        return;

    int min = (msize - size) / 2; // Left edge
    int max = min+size-1; // Right edge
    int i;
    row = col = max; // Start in bottom left corner

    // Reached middle, return!
    if (size == 1) {
        a[row][col] = value;
        return;
    }

    for (i = 0; i < size-1; ++i) {
        a[row][col--] = value--; // LEFT
    }

    for (i = 0; i < size-1; ++i) {
        a[row--][col] = value--; // UP
    }

    for (i = 0; i < size-1; ++i) {
        a[row][col++] = value--; // RIGHT
    }

    for (i = 0; i < size-1; ++i) {
        a[row++][col] = value--; // DOWN
    }

    buildMatrix(msize, a, size-2, value);
}

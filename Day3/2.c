#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int loopSpiral(int msize, int a[msize][msize], long target);

int calculateSum(int msize, int a[msize][msize], int x, int y);

int main(int argc, char **argv) {

    if (argc != 2) {
        printf("Usage %s input\n", argv[0]);
        exit(-1);
    }

    long input = strtol(argv[1], NULL, 10);

    // Create array and write zeros
    int matrix[600][600];
    for (int i = 0; i < 600; i++) {
        for (int j = 0; j < 5; j++) {
            matrix[i][j] = 0;
        }
    }

    int largerThanInput = loopSpiral(600, matrix, input);
    printf("%d\n", largerThanInput);
}

int loopSpiral(int msize, int a[msize][msize], long target) {

    // Set x,y to middle (and value 1)
    int x, y;
    x = y = msize/2;
    a[x][y] = 1;

    // Direction, start east
    int dx, dy;
    dx = 1, dy = 0;

    // Used to calculate step count
    _Bool newLayer = 1;
    int steps = 1;

    while (1) {
        
        for (int j = 0; j < steps; j++) {
        
           // "Walk"   
           x += dx;
           y += dy;
           
           // Calculate sum
           a[x][y] = calculateSum(msize, a, x, y);
           if (a[x][y] > target) {
               return a[x][y];             
           }
        }

        // Set direction for next iteration
        if (dx == 1) {
            dx = 0;
            dy = -1;
        } else if (dx == -1) {
            dx = 0;
            dy = 1;
        } else if (dy == 1) {
            dy = 0;
            dx = 1;
        } else if (dy == -1) {
            dy = 0;
            dx = -1;
        }

        // Increase step every 2nd
        newLayer = !newLayer;
        if (newLayer) {
            steps += 1;
        }
    }
}

int calculateSum(int msize, int a[msize][msize], int x, int y) {
    int sum = 0;

    sum += a[x][y-1]; // TOP
    sum += a[x-1][y-1]; // TOP-LEFT
    sum += a[x-1][y]; // LEFT
    sum += a[x-1][y+1]; // BOTTOM-LEFT
    sum += a[x][y+1]; // BOTTOM
    sum += a[x+1][y+1]; // BOTTOM-RIGHT
    sum += a[x+1][y]; // RIGHT
    sum += a[x+1][y-1]; // TOP-RIGHT

    return sum;
} 

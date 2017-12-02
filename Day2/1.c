#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <ctype.h>
#include <string.h>

int main(int argc, char **argv) {

    if (argc != 2) {
        printf("Usage %s filename\n", *argv);
        exit(-1);
    }

    FILE *fp;
    char *filename = *(argv+1);

    char *buffer;
    size_t bufsize = 1024;

    long int max, min, sum;
    sum = 0;

    fp = fopen(filename, "r");
    if (fp == NULL) {
        perror("Could not open file.\n");
        exit(1);
    }

    buffer = (char*) malloc(bufsize * sizeof(char));
    if (buffer == NULL) {
        perror("Unable to allocate buffer.\n");
        exit(1);
    }

    while (fgets(buffer, bufsize, fp) != NULL) {
        
        max = 0;
        min = LONG_MAX;

        char* p = buffer;
        while (p < buffer+bufsize) {
            char* end;
            long int value = strtol(p, &end, 10);
            if (value == 0L && end == p)
                break;

            if (value > max)
                max = value;
            if (value < min)
                min = value;

            p = end;
        }

        sum += max-min;
    }


    printf("Result: %ld\n", sum);

    free(buffer);
    fclose(fp);
}

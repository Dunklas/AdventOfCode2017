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

    long int sum = 0;

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

        int numValues = 0;
        long int* values = (long int*) malloc(20 * sizeof(long int));

        char* p = buffer;
        while (p < buffer+bufsize) {
            char* end;
            long int value = strtol(p, &end, 10);
            if (value == 0L && end == p)
                break;

            for (int i = 0; i < numValues; i++) {
                if (value % *(values+i) == 0) {
                    sum += value / *(values+i);
                } else if (*(values+i) % value == 0)
                    sum += *(values+i) / value;
            }

            *(values + numValues++) = value;
            
            p = end;
        }

        free(values);
    }


    printf("Result: %ld\n", sum);

    free(buffer);
    fclose(fp);
}

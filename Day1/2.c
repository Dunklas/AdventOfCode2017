#include <stdio.h>
#include <string.h>

int main(int argc, char **argv) {

    if (argc != 2) {
        printf("Usage %s input.\n", *argv);
    }

    char *input = *(argv+1);
    int length = strlen(input);
    int offset = length/2;

    long sum = 0;

    for (int i = 0; i < length; i++) {
        if (i+offset < length) {
            sum += input[i] == input[i+offset] ? input[i]-'0' : 0;
        } else {
            sum += input[i] == input[offset-(length-i)] ? input[i]-'0' : 0;
        }
    }
    
    printf("%ld\n", sum);
}

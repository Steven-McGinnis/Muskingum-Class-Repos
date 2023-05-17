/**
 * Author: Steven McGinnis
 * Email: stevenm1@muskingum.edu
 * Program that searches a directory and subdirectories for
 * a file and then if it finds it prints out all the data on the file
 * including (Inode, type, User ID, Group ID and size).
 */

#include <fcntl.h>
#include <dirent.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <errno.h>
#define BUFF_LEN 255

//Take the current Directory and the one being red and combine them to be returned.
char * append_dir(char * str1, char * str2){
    //Attachments for new Directory
    char pathChar[] = "/";
    char newLine[] = "\0";
    //Allocates the memory for the return string
    int newLength = strlen(str1) + strlen(str2);
    char* nextDirectory = malloc(newLength);
    int length = strlen(nextDirectory + 1);
    //Begin Concatinating each string together.
    strcat(nextDirectory, str1);
    strcat(nextDirectory, pathChar);
    strcat(nextDirectory, str2);
    strcat(nextDirectory, newLine);
    return nextDirectory;
}

//Pulls all the necessary information for the file passed and displays it to the user
void displayInfo(struct dirent * dent, char *directory){
    //Build the struct for stat
    struct stat buf;
    stat(dent->d_name, &buf);
    //All the line concatination with information
    printf("Directory Name: %s\n", directory);
    printf("Inode: %ld", buf.st_ino);
    printf("   Type %o\n", buf.st_mode);
    printf("User ID: %d", buf.st_uid);
    printf("  Group ID: %d", buf.st_gid);
    printf("  Size:  %ld", buf.st_size);
    printf(" bytes \n");
}
  

//Reads the directory that is passed to it and goes through the step to recursively search for a file.
void directorySearch(char * directory, char * fileName){
  //Directory Read Files
  DIR *dir;
  struct dirent *dent;
  dir = opendir(directory);
  if (dir != NULL) {
    while((dent=readdir(dir))!=NULL) {

      //If this is the file your looking for.
      if(strcmp(dent->d_name, fileName) == 0){
        displayInfo(dent, directory);
        exit(0);
      }
      
      //If the directory is current directory or up a level skip over it.
      if (strcmp(dent->d_name, ".") == 0 || strcmp(dent->d_name, "..") == 0)
        continue;

      //If the Dent is a directory do this.
      if (dent->d_type == DT_DIR){
        char *nextDirectory = append_dir(directory, dent->d_name);
        directorySearch(nextDirectory, fileName);
      }
    }
    closedir(dir);
  }
}

//The MAIN FUNCTION also known as (Driver)
int main (){
  char buff[BUFF_LEN];
  char *fileName = fgets(buff, BUFF_LEN, stdin);
  fileName[strlen(fileName)-1] = '\0';
  directorySearch(".", fileName);
  return 1;
}

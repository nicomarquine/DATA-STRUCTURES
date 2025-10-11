#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "PlayList.h"

int runDemo(void) {
    struct PlayList* p_playList = PlayList_new();
    PlayList_insertAtFront(p_playList, "Purple Rain");
    PlayList_insertAtFront(p_playList, "Jumping Jack Flash");
    PlayList_insertAtEnd(p_playList, "Like a Rolling Stone");
    PlayList_insertAfter(p_playList, "Jumping Jack Flash", "Stairway to Heaven");
    PlayList_insertBefore(p_playList, "Stairway to Heaven", "Bohemian Rhapsody");
    printf("Playlist:\n");
    PlayList_print(p_playList);
    PlayList_sort(p_playList);
    printf("\nAfter sorting:\n");
    PlayList_print(p_playList);
    printf("\n");

    struct PlayList* p_sortedPlayList2 = PlayList_new();
    PlayList_insertInOrder(p_sortedPlayList2, "Purple Rain");
    PlayList_insertInOrder(p_sortedPlayList2, "Jumping Jack Flash");
    PlayList_insertInOrder(p_sortedPlayList2, "Like a Rolling Stone");
    PlayList_insertInOrder(p_sortedPlayList2, "Stairway to Heaven");
    PlayList_insertInOrder(p_sortedPlayList2, "Bohemian Rhapsody");
    printf("Sorted Playlist:\n");
    PlayList_print(p_sortedPlayList2);
    
    PlayList_deleteSong(p_playList, "Stairway to Heaven");
    printf("\nAfter deleting Stairway to Heaven:\n");
    PlayList_print(p_playList);

    PlayList_deleteFromFront(p_playList);
    printf("\nAfter deleting from front:\n");
    PlayList_print(p_playList);

    char* song = PlayList_playingSong(p_playList);
    printf("\nPlaying song: %s\n", song);
    free(song);
    song = NULL;

    PlayList_playNext(p_playList);
    song = PlayList_playingSong(p_playList);
    printf("\nPlaying next song: %s\n", song);
    free(song);
    song = NULL;

    PlayList_free(&p_playList);
    PlayList_free(&p_sortedPlayList2);

    return 0;
}
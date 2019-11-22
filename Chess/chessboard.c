#include "chessboard.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void swap(struct chessboard *cb,char x1,int y1, char x2, int y2){
  cb->position[y2][x2]=cb->position[y1][x1];
  cb->position[y1][x1] = EMPTY;
}

////////// VALIDATION /////
int validate_empty(struct chessboard * cb,char x1,int y1, char x2, int y2){
  return 0;
}
int validate_white_pawn(struct chessboard * cb,char x1,int y1, char x2, int y2){
  if (y1==7 && y2 == y1+1 && cb->position[y1+1][x1] == EMPTY && x2==x1){
    cb->position[y1][x1]=WHITE_QUEEN;
    return 1;
  }

  if(y1==2 && y2 == y1+2 && x1 == x2 && cb->position[y2][x2] ==EMPTY && cb->position[y1+1][x2] ==EMPTY ){
    return 1;
  }

  else if(x1==x2 && y2 == y1+1 && cb->position[y2][x2] ==EMPTY){
    return 1;
  }

  else if((x2==x1-1 || x2==x1 +1) && y2== y1+1 && cb->position[y2][x2]!=EMPTY){
    return 1;
  }
  return 0;
}
int validate_white_rook(struct chessboard * cb,char x1,int y1, char x2, int y2){
  if(!(y2==y1 || x2 == x1)){
    return 0;
  }

  int j =1;
  if(x1==x2){
      if (y2<y1){
       j= -1;
      }
      y1 = y1+j;
  while (y1 != y2){

    if(cb->position[y1][x2]!=EMPTY){
      return 0;
    }
    else{y1 = y1+j;
    }
  }
  return 1;
}

  else if(y1==y2){
      if (x2<x1){
       j= -1;
      }
      x1 = x1+j;
  while (x1 != x2){

    if(cb->position[y1][x1]!=EMPTY){
      return 0;
    }
    else{
      x1 = x1+j;
    }

  }
  return 1;

  }
  return 0;
}
int validate_black_bishop(struct chessboard * cb,char x1,int y1, char x2, int y2){
  int j =1;
  int k =1;
      if(y2==y1 || x2 == x1){
        return 0;
      }
      if (y2<y1 && x2<x1){
       j= -1;
       k= -1;
      }
      else if(y2<y1 && x2>x1){
        j= -1;
      }
      else if(y2>y1 && x2<x1){
        k= -1;
      }
      else {
        k=1;
        j=1;
      }
      y1 = y1+j;
      x1 = x1+k;
    while (y1 != y2){
    if(cb->position[y1][x1]!=EMPTY){
      return 0;
    }
    else{
      y1 = y1+j;
      x1= x1+k;
    }
  }
  if (x1 != x2 || y1 != y2){
    return 0;
  }
  return 1;
}
int validate_white_bishop(struct chessboard * cb,char x1,int y1, char x2, int y2){
  int j =1;
  int k =1;

      if(y2==y1 || x2 == x1){
        return 0;
      }
      if (y2<y1 && x2<x1){
       j= -1;
       k= -1;
      }
      else if(y2<y1 && x2>x1){
        j= -1;
      }
      else if(y2>y1 && x2<x1){
        k= -1;
      }
      else {
        k=1;
        j=1;
      }
      y1 = y1+j;
      x1 = x1+k;
    while (y1 != y2){
    if(cb->position[y1][x1]!=EMPTY){
      return 0;
    }
    else{
      y1 = y1+j;
      x1= x1+k;
    }

  }

  if (x1 != x2 || y1 != y2){
    return 0;
  }
  return 1;


}
int validate_white_queen(struct chessboard * cb,char x1,int y1, char x2, int y2){
  return  (validate_white_rook(cb,x1,y1,x2,y2) || validate_white_bishop(cb,x1,y1,x2,y2));
}

int validate_white_knight(struct chessboard * cb,char x1,int y1, char x2, int y2){
  if(  (( (y2==y1+1) || (y2==y1-1) ) && (x2==x1-2))  ||  ( ( (y2==y1+1) || (y2==y1-1)) && (x2==x1+2))
    || ((y2==y1-2) && ( (x2 == x1-1) || (x2 == x1 +1)))
    || ( (y2==y1+2) && ( (x2 == x1-1)|| (x2 == x1 +1)))) {
    return 1;
  }

  return 0;
}
int validate_black_knight(struct chessboard * cb,char x1,int y1, char x2, int y2){
  if(  (( (y2==y1+1) || (y2==y1-1) ) && (x2==x1-2))  ||  ( ( (y2==y1+1) || (y2==y1-1)) && (x2==x1+2))
    || ((y2==y1-2) && ( (x2 == x1-1) || (x2 == x1 +1)))
    || ( (y2==y1+2) && ( (x2 == x1-1)|| (x2 == x1 +1)))) {
    return 1;
  }
  return 0;
}
int validate_black_pawn(struct chessboard * cb,char x1,int y1, char x2, int y2){
  if (y1==2 && y2 == y1-1 && cb->position[y1-1][x1] == EMPTY && x2==x1){
    cb->position[y1][x1]=BLACK_QUEEN;
    return 1;
  }
  if(y1==7 && y2 == y1-2 && x1 == x2 && cb->position[y2][x2] ==EMPTY && cb->position[y1-1][x2] ==EMPTY ){
    return 1;
  }

  else if(x1==x2 && y2 == y1-1 && cb->position[y2][x2] ==EMPTY){
    return 1;
  }
  else if((x2==x1-1 || x2==x1 +1) && y2== y1-1 && cb->position[y2][x2]!=EMPTY){
    return 1;
  }

  return 0;
}
int validate_black_rook(struct chessboard * cb,char x1,int y1, char x2, int y2){
  if(!(y2==y1 || x2 == x1)){
    return 0;
  }

  int j =1;
  if(x1==x2){
      if (y2<y1){
       j= -1;
      }
      y1 = y1+j;
  while (y1 != y2){

    if(cb->position[y1][x1]!=EMPTY){
      return 0;
    }
    else{
      y1 = y1+j;
    }

  }
  return 1;
}

  else if(y1==y2){

      if (x2<x1){
       j= -1;
      }
      x1 = x1+j;
  while (x1 != x2){

    if(cb->position[y1][x1]!=EMPTY){
      return 0;
    }
    else{
      x1 = x1+j;
    }

  }
  return 1;

  }
  return 0;
}


int validate_black_queen(struct chessboard * cb,char x1,int y1, char x2, int y2){

    if(validate_black_rook(cb,x1,y1,x2,y2)){
      return 1;}
    if(validate_black_bishop(cb,x1,y1,x2,y2)){
      return 1;
    }
    return 0;
}

int validate_white_king(struct chessboard * cb,char x1,int y1, char x2, int y2){

      if(y1==y2 && x2 ==x1+2){
        int i=1;
        while(i<3){
          if(cb->position[y1][x1+i]!=EMPTY){
            return INVALID;
          }
          i++;
        }
        swap(cb,'h',y1,x1+1,y1);
        return 1;
      }

      if(y1==y2 && x2 ==x1-2){
        int i=1;
        while(i<4){
          if(cb->position[y1][x1-i]!=EMPTY){
            return INVALID;
          }
          i++;
        }
        swap(cb,'a',y1,x1-1,y1);
        return 1;
      }
  if(( (x2==x1+1) && y2==y1)||((x2==x1-1) &&y2==y1) || (x2==x1 && (y2==y1+1))||( x2==x1 && (y2==y1-1)) ||
        ((x2==x1+1) && (y2==y1+1)) || ((x2==x1+1)&& (y2==y1-1) ) || ((x2==x1-1) && (y2==y1+1)) || ((x2==x1-1) &&(y2==y1-1))) {

    return 1;
    }

    return 0;
  }
int validate_black_king(struct chessboard * cb,char x1,int y1, char x2, int y2){

  if(y1==y2 && x2 ==x1+2){
    int i=1;
    while(i<3){
      if(cb->position[y1][x1+i]!=EMPTY){
        return INVALID;
      }
      i++;
    }
    swap(cb,'h',y1,x1+1,y1);
    return 1;
  }

  if(y1==y2 && x2 ==x1-2){
    int i=1;
    while(i<4){
      if(cb->position[y1][x1-i]!=EMPTY){
        return INVALID;
      }
      i++;
    }
    swap(cb,'a',y1,x1-1,y1);
    return 1;
  }

  if(( (x2==x1+1) && y2==y1)||((x2==x1-1) &&y2==y1) || (x2==x1 && (y2==y1+1))||( x2==x1 && (y2==y1-1)) ||
        ((x2==x1+1) && (y2==y1+1)) || ((x2==x1+1)&& (y2==y1-1) ) || ((x2==x1-1) && (y2==y1+1)) || ((x2==x1-1) &&(y2==y1-1))) {
    return 1;
    }
  return 0;

}

int (*validate[13])(struct chessboard *,char,int,char,int) ={&validate_empty,&validate_white_pawn,&validate_white_knight,&validate_white_bishop,&validate_white_rook,&validate_white_queen,&validate_white_king,
&validate_black_pawn,&validate_black_knight,&validate_black_bishop,&validate_black_rook,&validate_black_queen,&validate_black_king};


static int black_king_moved=0;
static int white_king_moved=0;
static int left_white_rook_moved=0;
static int left_black_rook_moved=0;
static int right_white_rook_moved=0;
static int right_black_rook_moved =0;
enum mstatus move(struct chessboard * cb,enum player p, const char * from, const char * to){

  int valid;
  char x1 =from[0];
  int y1 =(from[1] - '0');
  char x2=to[0];
  int y2 = (to[1]-'0');

    //King position
    int pos1_king;
    char pos2_king;
    enum pieces king=(p)? BLACK_KING:WHITE_KING;

    enum pieces piece = cb->position[y1][x1];
    enum pieces destination = cb->position[y2][x2];

    if(p == WHITE && (piece >6 || (destination <= 6 && destination !=0))) {
      return INVALID;
    }
    else if (p == BLACK && (piece <6 || destination >6 )){
      return INVALID;
    }

    else{
      /// CHECK FOR CASTLING
      int moved = (p)? black_king_moved:white_king_moved;
      int left = (p)? left_black_rook_moved:left_white_rook_moved;
      int right = (p)? right_black_rook_moved:right_white_rook_moved;

      if(piece==king && !moved){
        if(y1 == y2 && x2==x1+2){
          if(right)return INVALID;
        }
        if(y1 == y2 && x2==x1-2) {
          if(left) return INVALID;
        }

      }

      ///  This is the coolest function that replaced a big Switch statement into a single line;
      valid = validate[piece](cb,x1,y1,x2,y2);

    }

    if(valid==0){
      return INVALID;
    }
    else if (valid) {

      piece =cb->position[y1][x1];
      cb->position[y1][x1] = EMPTY;
      int lbound=(king==6)?13:6;
      int ubound =(king==6)?6:0;
      int reserve=cb->position[y2][x2];
      cb->position[y2][x2]=piece;


      for (int i =8; i>0 ; --i){
        for (int j='a'; j<'i'; ++j){
          if(cb->position[i][j]==king){
            pos1_king=i;
            pos2_king=j;
          }

        }
      }


      /// CHECK IF THAT MOVE LEAVES THE KING IN CHECK
      for (int i =8; i>0 ; --i){
        for (int j='a'; j<'i'; ++j){
          if(cb->position[i][j]!=EMPTY && cb->position[i][j]>ubound && cb->position[i][j]<lbound){
            if(validate[cb->position[i][j]](cb,j,i,pos2_king,pos1_king)) {
              cb->position[y2][x2]=reserve;
              cb->position[y1][x1]=piece;
              return 0;}
          }

        }
      }

      cb->position[y2][x2]=reserve;
      cb->position[y1][x1]=piece;
      swap(cb,x1,y1,x2,y2);

      //Conditions for CASTLING
      switch(piece){
        case WHITE_KING: white_king_moved =1;break;
        case BLACK_KING: black_king_moved =1;break;
        case WHITE_ROOK: if (x1=='a'){left_white_rook_moved =1;} else{right_white_rook_moved++;}break;
        case BLACK_ROOK:(x1=='a')? left_black_rook_moved =1:right_black_rook_moved;break;
        case WHITE_PAWN:
        case WHITE_QUEEN:
        case WHITE_KNIGHT:
        case WHITE_BISHOP:
        case BLACK_PAWN:
        case BLACK_QUEEN:
        case BLACK_KNIGHT:
        case BLACK_BISHOP:
        case EMPTY: break;


      }


      // CHECK if after the move, you put the opponent king in CHECK
      int K1= (p)?WHITE_KING:BLACK_KING;
      for (int i=8;i>0;--i){
        for (char j='a';j<='h';++j){
          if(cb->position[i][j]==K1){
            if (validate[piece](cb,x2,y2,j,i)){
              return CHECK;
            }
          }
        }
      }
      return 1;

    }

  return INVALID;
}


void print_chessboard(struct chessboard * cb){
  printf("   a    b   c   d   e   f   g   h\n");
  printf("  _________________________________\n");
  for (int i =8; i>0; --i){
    printf("%d",i);
      printf(" |");
      for (int j='a'; j<'i'; ++j){
      const static char* utf_8_pieces[13] = {" ", "♙", "♘", "♗", "♖", "♕", "♔", "♟", "♞", "♝", "♜", "♛", "♚" };
          enum pieces piece = cb->position[i][j];
            switch (piece) {
          case EMPTY:        (j!='a')?printf("  | "):printf("   | ");
                             break;
          case WHITE_PAWN: (j!='a')?printf("♙ | "):printf(" ♙ | ");
                             break;
          case WHITE_QUEEN:        (j!='a')?printf("♕ | "):printf(" ♕ | ");
                             break;
          case WHITE_KNIGHT:        (j!='a')?printf("♘ | "):printf(" ♘ | ");
                             break;
          case WHITE_BISHOP:       (j!='a')?printf("♗ | "):printf(" ♗ | ");
                             break;
          case WHITE_ROOK:       (j!='a')?printf("♖ | "):printf(" ♖ | ");
                             break;
          case WHITE_KING:         (j!='a')?printf("♔ | "):printf(" ♔ | ");
                             break;
          case BLACK_PAWN:   (j!='a')?printf("♟ | "):printf(" ♟ | ");
                             break;
          case BLACK_ROOK:       (j!='a')?printf("♜ | "):printf(" ♜ | ");
                            break;
          case BLACK_BISHOP:  (j!='a')?printf("♝ | "):printf(" ♝ | ");
                            break;
          case BLACK_KNIGHT:   (j!='a')?printf("♞ | "):printf(" ♞ | ");
                            break;
         case BLACK_QUEEN:  (j!='a')?printf("♛ | "):printf(" ♛ | ");
                           break;
         case BLACK_KING:   (j!='a')?printf("♚ | "):printf(" ♚ | ");
                           break;

        }
      }
      printf("\n");
        printf("  |___|___|___|___|___|___|___|___|\n");
  }
  printf("   a    b   c   d   e   f   g   h\n");
}


void init_chessboard(struct chessboard * cb){
    //cb =malloc(sizeof(struct chessboard));
    for (int i =8; i>0 ; --i){
      for (int j='a'; j<'i'; ++j){
         if (i == 7){
          cb->position[i][j]=BLACK_PAWN;
        }
        else if (i == 2){
         cb->position[i][j]=WHITE_PAWN;
       }
       else if(i ==1){
         switch(j){
           case 'a':
           case 'h': cb->position[i][j]=WHITE_ROOK; break;
           case 'b':
           case 'g': cb->position[i][j]=WHITE_KNIGHT; break;
           case 'c':
           case 'f': cb->position[i][j]=WHITE_BISHOP; break;
           case 'd': cb->position[i][j]=WHITE_QUEEN; break;
           case 'e': cb->position[i][j]=WHITE_KING; break;

         }
       }
       else if(i ==8){
         switch(j){
           case 'a':
           case 'h': cb->position[i][j]=BLACK_ROOK; break;
           case 'b':
           case 'g': cb->position[i][j]=BLACK_KNIGHT; break;
           case 'c':
           case 'f': cb->position[i][j]=BLACK_BISHOP; break;
           case 'd': cb->position[i][j]=BLACK_QUEEN; break;
           case 'e': cb->position[i][j]=BLACK_KING; break;

         }
       }
       else{
           cb->position[i][j]=EMPTY;
         }

        }
    }

    print_chessboard(cb);

}


void split_string(char * inputString,char * from, char *to){
  int length, mid, i, k;

  length = strlen(inputString);
  mid = length/2;
  for(i = 0; i < mid; i++) {
      from[i]= inputString[i];
  }
  from[i] = '\0';
  for(i = mid+1, k = 0; i <= length; i++, k++) {
    to[k]= inputString[i];
  }
}


int main() {
  struct chessboard *cb =malloc(sizeof(struct chessboard));

    if(!cb){
      return 0;
    }
    init_chessboard(cb);
    enum player p = WHITE;
    int flag=1;

  while(flag){
    char inputString[20], from[10], to[10];
    gets(inputString);
    if(strcmp(inputString,"resign")==0){
      printf("%c\n",(!p)?'b':'w');
      break;
    }
    else if(strcmp(inputString,"draw")==0){
      gets(inputString);
      /// Check if the opponent agreed to draw
      if(strcmp(inputString,"draw")==0){
        printf("d\n");
        break;
      }
    }

    else{

    split_string(inputString,from,to);
     //swap(&cb,from,to);
  enum mstatus ms= move(cb,p,from,to);
      switch (ms) {
        case INVALID:printf("INVALID INPUT\n");
                    break;
        case VALID:
        print_chessboard(cb);
                      p=!p;
                      printf("player: %s\n",(p)? "b":"w");
                    break;
        case CHECK: printf ("CHECK\n");

                    enum pieces position2[8][8];

                    /*Save the current state in a copy
                      Without this the programm figured it out itself
                      how to get out from check, and made the move :))
                    */
                    for (int k =8; k>0 ; --k){
                      for (int g='a'; g<'i'; ++g){
                        position2[k][g]=cb->position[k][g];
                      }
                    }

                    int lbound=(!p)?13:6;
                    int ubound =(!p)?6:0;
                    int flag3 =1;
                    int flag4=1;
                    int flag2=1;
                    int flag5=0;

                    char from1[3]="bb";
                    char to1[3]="bb";

                    ///Check if there is a possible move to get out from CHECK
                    for (int i =8; i>0 ; --i){
                      if (flag5){break;}
                      if (flag4){
                      for (int j='a'; j<'i'; ++j){
                        if(flag3 && cb->position[i][j]!=EMPTY && cb->position[i][j]>ubound && cb->position[i][j]<lbound){
                          for (int k =8; k>0 ; --k){
                            for (int g='a'; g<'i'; ++g){
                              from1[0]=j;
                              from1[1]=i+'0';
                              to1[0]=g;
                              to1[1]=k+'0';
                              if (move(cb,!p,from1,to1) == VALID){
                                flag2=0;
                                flag3=0;
                                flag4=0;
                                flag5=1;
                                break;
                              }

                            }
                          }

                        }
                      }
                    }
                    }


                      if (flag2){
                      printf("\n%c\n",(p)? 'b':'w'); flag =0; free(cb); break;
                    }
                    else  {
                      //*cb = cb_copy;
                      for (int k =8; k>0 ; --k){
                        for (int g='a'; g<'i'; ++g){
                          cb->position[k][g]=position2[k][g];
                        }
                      }
                      print_chessboard(cb);
                      p=!p;
                      printf("player: %s\n",(p)? "b":"w");
                      break;}


        case CHECK_MATE:printf("\n%c\n",(p)? 'b':'w'); flag =0; free(cb); break;

      }
  }
}

  return 0;
}

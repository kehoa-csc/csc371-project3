from random import *


def method_one():
    #0 is a solid wall, 1 is empty path
    level = [[0,0,0,0,0,0,0,0],
             [0,0,0,0,0,0,0,0],
             [0,0,0,0,0,0,0,0],
             [0,0,0,0,0,0,0,0],
             [0,0,0,0,0,0,0,0],
             [0,0,0,0,0,0,0,0]]

    height=len(level)
    width=len(level[0])

    #first 2 column always has 1
    entry = randrange(height-1)
    level[entry][0] = 1
    level[entry][1] = 1

    y=entry
    x=1 #[y, x]

    opp_dirs=['down','up','right','left']
    last_move='right'

    while x<7:
        all_dirs=['up','down','left','right']
        valid_dirs=all_dirs

        #remove the opposite of last move from possible moves to pick
        valid_dirs.remove(opp_dirs[all_dirs.index(last_move)])

        #make right twice as likely
        if valid_dirs.__contains__('right'):
            valid_dirs.append('right')

        #pick a direction
        to_move = valid_dirs[randrange(len(valid_dirs))]
        print("moving",to_move)

        match to_move:
            case 'up':
                if y>0:
                    y-=1
            case 'down':
                if y<height-1:
                    y+=1
            case 'left':
                if x>1:
                    x-=1
            case 'right':
                x+=1

        level[y][x]=1
        last_move=to_move
    print(level)

    #pathfind
    x = 1
    y = entry

    level[entry][0] = 2
    level[entry][1] = 2

    to_move = 'right'
    hit_wall = False
    #keep moving until hit wall or finish level
    success = False

    while not success:
        runs = 0
        while x<7 and runs<100:
            runs += 1
            if not hit_wall:
                match to_move:
                    case 'up':
                        if y>0:
                            if y==0 or level[y-1][x]==0:
                                hit_wall = True
                            else:
                                y-=1
                                level[y][x]=2
                        else:
                            hit_wall = True
                    case 'down':
                        if y<height-1:
                            if y==height-1 or level[y+1][x]==0:
                                hit_wall = True
                            else:
                                y+=1
                                level[y][x]=2
                        else:
                            hit_wall = True
                    case 'left':
                        if x>1:
                            if x==1 or level[y][x-1]==0:
                                hit_wall = True
                            else:
                                x-=1
                                level[y][x]=2
                        else:
                            hit_wall = True
                    case 'right':
                        if x<8:
                            if level[y][x+1]==0:
                                hit_wall = True
                            else:
                                x+=1
                                level[y][x]=2
            else: #correct course
                match to_move:
                    case 'up' | 'down':
                        if x==0:
                            to_move = 'right'
                        elif x>width-1:
                            to_move = 'left'
                        elif level[y][x+1]==1 and level[y][x-1]==1:
                            to_move = choice(['left','right'])
                        elif level[y][x+1]==1:
                            to_move='right'
                        elif level[y][x-1]==1:
                            to_move='left'
                    case 'left' | 'right':
                        if y==0:
                            to_move='down'
                        elif y==height-1:
                            to_move='up'
                        elif level[y-1][x]==1 and level[y+1][x]==1:
                            to_move = choice(['up','down'])
                        elif level[y-1][x]==1:
                            to_move='up'
                        elif level[y+1][x]==1:
                            to_move='down'
                hit_wall = False
            if x==7:
                success = True






    print(level)



method_one()
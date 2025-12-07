package com.example.codecraftjr

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlin.random.Random

class LevelGenerator {

    fun generateLevel(hard_mode: Boolean = false): SnapshotStateList<SnapshotStateList<Int>> {
        val level = mutableStateListOf(
            mutableStateListOf(0,0,0,0,0,0,0,0),
            mutableStateListOf(0,0,0,0,0,0,0,0),
            mutableStateListOf(0,0,0,0,0,0,0,0),
            mutableStateListOf(0,0,0,0,0,0,0,0),
            mutableStateListOf(0,0,0,0,0,0,0,0),
            mutableStateListOf(0,0,0,0,0,0,0,0),
        )

        var success = false
        while(!success) {
            val height = level.size
            val width = level[0].size

            val entry = Random.nextInt(height-1)
            level[entry][0] = 1
            level[entry][1] = 1

            var y=entry
            var x=1

            val all_dirs=listOf("up","down","left","right")
            val opp_dirs=listOf("down","up","right","left")
            var valid_dirs: List<String>
            var last_move = "right"
            var to_move: String

            while(x<width-1) {
                valid_dirs = all_dirs
                valid_dirs.drop(opp_dirs.indexOf(last_move))

                to_move = valid_dirs[Random.nextInt(valid_dirs.size)]

                if (to_move == "up") {
                    if (y>0) {
                        y-=1
                    }
                } else if (to_move == "down") {
                    if (y<height-1) {
                        y+=1
                    }
                } else if (to_move == "left") {
                    if (x>1) {
                        x-=1
                    }
                } else if (to_move == "right") {
                    x+=1
                }

                level[y][x] = 1
                last_move = to_move
            }

            //ToDo: if hardmode=false set success to true and break

            //pathfinding
            x=0
            y=entry

            level[entry][0] = 2
            level[entry][1] = 2

            to_move = "right"
            var hit_wall = false

            //keep moving until hit wall or finish level
            var runs = 0
            while(x<width-1 && runs<100) {
                runs++

                if (!hit_wall) {
                    if (to_move == "up") {
                        if (y>0) {
                            if (level[y-1][x]==0) {
                                hit_wall=true
                            } else {
                                y-=1
                                level[y][x]=2
                            }
                        } else {
                            hit_wall=true
                        }
                    } else if (to_move == "down") {
                        if (y<height-1) {
                            if (y==height-1 || level[y+1][x]==0) {
                                hit_wall = true
                            } else {
                                y+=1
                                level[y][x] = 2
                            }
                        } else {
                            hit_wall = true
                        }
                    } else if (to_move == "left") {
                        if (x>1) {
                            if (level[y][x-1]==0) {
                                hit_wall = true
                            } else {
                                x-=1
                                level[y][x] = 2
                            }
                        } else {
                            hit_wall = true
                        }
                    } else if (to_move == "right") {
                        if (x<8) {
                            if (level[y][x+1]==0) {
                                hit_wall = true
                            } else {
                                x+=1
                                level[y][x] = 2
                            }
                        }
                    }
                } else { //correct course
                    if (to_move == "up" || to_move == "down") {
                        if (x==0) {
                            to_move="right"
                        } else if (x>width-1) {
                            to_move="left"
                        } else if (level[y][x+1]==1 && level[y][x-1]==1) {
                            to_move=listOf("left","right")[Random.nextInt(2)]
                        } else if (level[y][x+1]==1){
                            to_move="right"
                        } else if (level[y][x-1]==1) {
                            to_move="left"
                        }
                    } else if (to_move == "left" || to_move == "right") {
                        if (y==0) {
                            to_move="down"
                        } else if (y==height-1) {
                            to_move="up"
                        } else if (level[y-1][x]==1 && level[y+1][x]==1) {
                            to_move=listOf("up","down")[Random.nextInt(2)]
                        } else if (level[y+1][x]==1){
                            to_move="down"
                        } else if (level[y-1][x]==1) {
                            to_move="up"
                        }
                    }
                    hit_wall=false
                }
                if (x==width-1) {
                    success=true
                    for (i in 0..<height) {
                        var all_ones=true
                        for (j in 0..<width) {
                            if (level[i][j]==0) {
                                all_ones=false
                            }
                        }
                        if (all_ones) {
                            success=false
                        }
                    }
                    var all_zeros = true
                    for (i in 0..<height) {
                        if (level[i][width - 1] != 0) {
                            all_zeros = false
                        }
                    }
                    if(all_zeros) {
                        success=false
                    }


                }
            }
            //clear excess generated level
            for(i in 0..<height) {
                for(j in 0..<width) {
                    if (level[i][j]==1) {
                        level[i][j]=0
                    }
                }
            }
            //replace 2s (path found) with 1s
            for(i in 0..<height) {
                for(j in 0..<width) {
                    if (level[i][j]==2) {
                        level[i][j]=1
                    }
                }
            }


        }

        return level
    }

}
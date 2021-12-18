import os.path
f = open(os.path.dirname(__file__)+"\..\..\Day01.txt","r")
lines = f.readlines()

prev_line = ''
count = 0

for line in lines:
        if prev_line < line:
                count+=1
        prev_line = line

print('Part one answer :',count)
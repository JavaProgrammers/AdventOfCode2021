import sys

if len(sys.argv) != 2:
    print("No input file found!")
    exit(0)

input_file = open(sys.argv[1], "r")
horizontal = 0
depth = 0
aim = 0
for line in input_file.readlines():
    line = line.strip()
    if not line:
        continue
    command = line.split()
    command_name = command[0]
    command_value = int(command[1])
    if command_name.lower() == "forward":
        horizontal += command_value
        depth += aim * command_value
    elif command_name.lower() == "up":
        aim -= command_value
    elif command_name.lower() == "down":
        aim += command_value
    else:
        print("Unknown Command")
        exit(-1)
print("Result: ", depth * horizontal)

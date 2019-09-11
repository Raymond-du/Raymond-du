CPPFLAGS=
CFLAGS=-g -Wall
LDFLAGS=
CC=g++

src=$(wildcard *.cpp)
obj=$(patsubst %.cpp,%.o,$(src))

target=poll

$(target):$(obj)
	$(CC) $(obj) $(LDFLAGS) -o $(target)

%.o:%.cpp
	$(CC) -c $(CFLAGS) $< -o $@

clean: 
	rm *.o $(target)

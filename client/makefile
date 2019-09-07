CPPFLAGS=
CFLAGS=-O3 -Wall
LDFLAGS=
CC=g++

src=$(wildcard *.cpp)
obj=$(patsubst %.cpp,%.o,$(src))

target=client

$(target):$(obj)
	$(CC) $(obj) $(LDFLAGS) -o $(target)
%.o:%.cpp
	$(CC) -c $(CFLAGS) $< -o $@
clean: 
	rm *.o $(target)

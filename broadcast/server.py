'''UDP server code'''
'''Listens for UDP packets'''
import socket
import sys

ip = ''
port = 12346

try:
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
except socket.error:
    print "socket creation failed!"
    sys.exit()

sock.bind((ip, port))

print "Receiving message..."
while 1:
    data, addr = sock.recvfrom(1024)
    print data, addr



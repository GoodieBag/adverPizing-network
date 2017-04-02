'''UDP Broadcast cliet code'''
'''Broadcasts message 'Hello' every 5 seconds'''

import socket
import time
import sys

ip = ''
port = 9000

message = "Hello!"
try:
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
except socket.error:
    print "Socket creation failed!"
    sys.exit()

'''Set socket options for Broadcast'''
sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
sock.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

while 1:
    print "Broadcasting message....."
    sock.sendto(message, ("255.255.255.255", 12346))
    print "Done"
    time.sleep(5)
    

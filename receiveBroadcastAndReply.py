'''Script receives broadcast data from phone and replies to 
   it with PiZing's IP'''

from socket import *
import time

#Get Pi IP
ipsock = socket(AF_INET, SOCK_DGRAM)
ipsock.connect(("8.8.8.8", 80))
piIp = ipsock.getsockname()[0]
print "PiZing IP: " +piIp

receivePort = 9000
sendPort = 12345
messageToClient = ""
clientIp = ""

sock = socket(AF_INET, SOCK_DGRAM)
sock.bind(("", receivePort))
print "Bind done"

print "Receiving message..."
while 1:
    data, addr = sock.recvfrom(1024)
    print "Data received: " +data
    clientIp = addr[0]
    #if broadcast data is as expected, reply with IP
    if data == "Hello Pi! Whats your IP?":
        messageToClient = piIp
        print "Replying..."
        print "Sending PiZing IP to Client: " +messageToClient
        sock.sendto(messageToClient, (clientIp, sendPort))
        time.sleep(1)
        print "IP sent!"

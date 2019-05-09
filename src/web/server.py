from flask import Flask, send_from_directory, request, render_template
import json
import socket
from threading import Thread
from random import randint
from flask_socketio import SocketIO
import eventlet

eventlet.monkey_patch()
app = Flask(__name__)
socket_server = SocketIO(app)
usernameToSid = {}
sidToUsername = {}
scala_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
scala_socket.connect(('localhost', 8000))
delimiter = "~"


def listen_to_scala(the_socket):
    buffer = ""
    while True:
        buffer += the_socket.recv(1024).decode()
        while delimiter in buffer:
            message = buffer[:buffer.find(delimiter)]
            buffer = buffer[buffer.find(delimiter) + 1:]
            get_from_scala(message)


def get_from_scala(data):
    socket_server.emit('gameState', data, broadcast=True)


def send_to_scala(data):
    scala_socket.sendall((json.dumps(data) + delimiter).encode())


Thread(target=listen_to_scala, args=(scala_socket,)).start()


# ** Setup and start Python web server **
@socket_server.on('register')
def got_message(username):
    # print(username)
    sidToUsername[request.sid] = username
    print(sidToUsername[request.sid] + " connected")
    # print(username)
    # print("/////////")
    message = {"username": username, "action": "connected"}
    send_to_scala(message)


@socket_server.on('disconnect')
def disconnect():
    print(sidToUsername[request.sid] + " disconnected")
    message = {"username": sidToUsername[request.sid], "action": "disconnected"}
    send_to_scala(message)


@socket_server.on('keyStates')
def key_state(jsonKeyStates):
    key_states = json.loads(jsonKeyStates)
    # print(key_state)
    # print(key_state["a"])
    x = 0.0
    if key_states["a"] and not key_states["d"]:
        x = -1.0
    elif not key_states["a"] and key_states["d"]:
        x = 1.0
    y = 0.0
    if key_states["w"] and not key_states["s"]:
        y = -1.0
    elif not key_states["w"] and key_states["s"]:
        y = 1.0
    message = {"username": sidToUsername[request.sid], "action": "move", "x": x, "y": y}
    # print(message)
    send_to_scala(message)


@socket_server.on('keyState')
def keystate(key, username):
    key_comparator = key
    x = 0.0
    y = 0.0
    if key_comparator == "w":
        y = -1
    if key_comparator == "a":
        x = -1
    if key_comparator == "s":
        y = 1
    if key_comparator == "d":
        x = 1
    message_to_send = {"username": username, "action": "move", "x": x, "y": y}
    send_to_scala(message_to_send)


@app.route('/')
def index():
    return send_from_directory('static', 'index.html')


@app.route('/game', methods=["POST", "GET"])
def game():
    if request.method == "POST":
        username = request.form.get('username')
    else:
        username = "guest" + str(randint(0, 100000))
    return render_template('game.html', username=username)


@app.route('/<path:filename>')
def static_files(filename):
    return send_from_directory('static', filename)


print("Listening on port 8080")
socket_server.run(app, port=8080)

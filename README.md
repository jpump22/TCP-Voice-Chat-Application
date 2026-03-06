# Java Real-Time Voice Chat

A real-time voice communication application built in Java using TCP sockets and the Java Sound API that allows two machines on the same network to communicate using live audio streaming. This project demonstrates socket programming, audio processing, and multithreading in a client-server architecture.

## Project Overview

This project implements a simple voice chat system that enables two computers to communicate directly over a network using real-time audio streaming.

The program can run in either **server mode** or **client mode**. One machine starts as the server and waits for a connection, while the second machine connects as the client. Once connected, both machines can send and receive live audio simultaneously.

This project builds upon a previous networking assignment that implemented text-based communication using UDP sockets. In this version, the system was extended to support **real-time voice communication**.

## Technologies Used

- Java
- TCP Socket Programming
- Java Sound API
- Client-Server Networking
- Multithreading
- Real-Time Audio Streaming

## System Architecture

The program operates using a client-server architecture.

### Server

The server:

- Opens a server socket on port `5000`
- Waits for a client to connect
- Starts real-time audio communication once the connection is established

### Client

The client:

- Prompts the user for the server's IP address
- Connects to the server using a socket
- Begins real-time audio communication with the server

### Audio Communication

The application captures and plays audio using the Java Sound API.

Two threads are used to allow simultaneous communication:

- **Send Thread**
  - Captures audio from the microphone
  - Sends audio data to the connected machine

- **Receive Thread**
  - Receives incoming audio data
  - Plays the audio through the system speakers

This allows both users to speak and hear audio at the same time.

## Repository Structure

```
voice-chat-java
│
├── project2.java
└── README.md
```

## How to Run the Program

### 1. Compile the program

```
javac project2.java
```

### 2. Start the program

```
java project2
```

### 3. Choose the role

When prompted, enter:

```
server
```

or

```
client
```

### 4. Connect the machines

- The **server** waits for a connection.
- The **client** enters the server's IP address.

Once connected, both machines can communicate through live voice audio.

## Demonstration Setup

The project was demonstrated using **two separate computers** connected to the same network.

One machine ran the program in **server mode**, while the other ran it in **client mode**. Once connected, both systems were able to transmit and receive audio in real time.

## Concepts Demonstrated

- Socket programming using TCP
- Real-time audio streaming
- Multithreaded communication
- Client-server network architecture
- Microphone audio capture and playback

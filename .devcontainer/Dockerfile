#
# Copyright (C) 2024 Jannusch Bigge <jannusch.bigge@tu-dresden.de>
#
# SPDX-License-Identifier: GPL-2.0-or-later
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
#
# Dockerfile for TSNsched and gRPC Server

FROM ubuntu:24.10


RUN echo 'Installing dependencies'
RUN apt-get update -y && apt-get upgrade -y && apt-get install -y openjdk-21-jdk git python3-setuptools build-essential wget unzip

RUN echo 'Install Z3'
WORKDIR /opt
RUN git clone https://github.com/Z3Prover/z3.git
WORKDIR /opt/z3 
RUN git checkout z3-4.13.0
RUN python3 scripts/mk_make.py --java
WORKDIR /opt/z3/build
RUN make 
RUN make install

RUN echo 'Installing dependencies of TsnSched fork'
WORKDIR /opt

RUN wget https://services.gradle.org/distributions/gradle-8.9-bin.zip 
RUN echo "d725d707bfabd4dfdc958c624003b3c80accc03f7037b5122c4b1d0ef15cecab gradle-8.9-bin.zip" | sha256sum --check --status
RUN unzip -d /opt/gradle gradle-8.9-bin.zip
RUN ln -s /opt/gradle/gradle-8.9 /opt/gradle/latest
ENV PATH="$PATH:/opt/gradle/latest/bin"

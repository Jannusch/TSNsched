#
# Copyright (C) 2023 Jannusch Bigge
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

#!/usr/bin/env bash
	apt-get -y install bash

	apt-get -y install bash

#define remote repo for z3 libary
z3Libary="https:#github.com/Z3Prover/z3.git"

set -e

# install dependencies
pacman -S --noconfirm jdk-openjdk


cd /opt

printf "Cloning z3 libary. "
git clone ${z3Libary}

cd z3


#run mk_make script
printf "Run Python make Script. Building Z3 using make and clang\n"
CXX=clang++ CC=clang python scripts/mk_make.py --java
cd build; make
make install

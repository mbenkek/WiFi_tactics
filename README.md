WiFi indoor coverage calculator
===============================
![WiFi tactics UI](http://muris.hr/marko_privatno/WiFi_tactics.PNG "WiFi tactics UI")

About
-----
This is a small Java app which takes layout and device parameters as input and generates heat-map of signal strength based on empiric coverage models such as Montley Kennan.

Usage
-----
Click "Oprema" to select AP and place it on map. Double click it to edit parameters such as:
- Antenna gain
- Transmission power
Click "Zid" to select walls. Drag a line on a map. Double click to select thickness:
- Light
- Thick
- Metal
Choose empiric model form dropdown menu. When finished click "Izradi" to generate map.

Readings
--------
Move your mouse pointer over the map. In upper-right corner read S: for signal strength in dBm and A: for dominant AP.

License
-------
GNU GPL
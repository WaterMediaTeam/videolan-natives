# VLC4J: VideoLAN For JAVA
This project is a fork of [VLCJ-natives](https://github.com/caprica/vlcj-natives) designed to enhance compatibility, binary discovery and
performance for direct LibVLC calls (instead of API layers like VLCJ).

Development is focused for [WATERMeDIA](https://github.com/WaterMediaTeam/watermedia) project, do not expect support for external projects.
Please consider use and support VLCJ.

**This library has the next purposes:**
- Provide native methods of LibVLC
- Perform binary discovery
- Callback spec and management
- Buffer allocation

## JVM ARGUMENTS
- `vlc4j.userDiscoveryPath=/usr/bin/custom.videolan.path/path`
  - Set custom VLC path (for spaces use `%20`). Example: ``/path/folder with spaces/vlc`` -> `/path/folder%20with%20spaces/vlc`

### LICENSE
**VideoLan4J** is under GLPv3 following upstream (vlcj) license
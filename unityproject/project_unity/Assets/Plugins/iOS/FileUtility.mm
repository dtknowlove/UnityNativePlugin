//
//  FileUtility.m
//  Unity-iPhone
//
//  Created by dtknowlove on 2020/1/15.
//
//

#include <sys/param.h>
#include <sys/mount.h>

extern "C"
{
   long _getFreeDiskSpace()
    {
        struct statfs buf;
        long long freespace = -1;
        if(statfs("/var", &buf) >= 0)
        {
            freespace = (long long)(buf.f_bsize * buf.f_bfree);
        }
        return freespace/(1024 * 1024);
    }
}

using System;
using System.Runtime.InteropServices;
using AOT;
using UnityEngine;

public class SampleiOS : MonoBehaviour
{
	void Start()
	{
#if UNITY_IOS
		outputAppendString("Hello", " world!");
		ResultHandler handler = ProccessResultHander;
		IntPtr intPtr = Marshal.GetFunctionPointerForDelegate(handler);
		outputAppendString2("回调", "走了吗？", intPtr);
#endif
	}

	void Callback(string str)
	{
		Debug.LogFormat("receive string:{0}", str);
	}

	[UnmanagedFunctionPointer(CallingConvention.Cdecl)]
	public delegate void ResultHandler(string resultString);

	[MonoPInvokeCallback(typeof(ResultHandler))]
	static void ProccessResultHander(string resultString)
	{
		Debug.LogFormat("Execute receive string:{0}", resultString);
	}

	[DllImport("__Internal")]
	static extern void outputAppendString(string str1, string str2);
	[DllImport("__Internal")]
	static extern void outputAppendString2(string str1, string str2,IntPtr resultHandler);
}

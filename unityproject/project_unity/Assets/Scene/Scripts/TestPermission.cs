using UnityEngine;
using UnityEngine.UI;

public class TestPermission : MonoBehaviour
{
	public Button BtnCamera, BtnMicrophone, BtnCalendar, BtnLoaction, BtnCorseLocation, BtnStorage, BtnBluetooth, BtnPop;

	private AndroidJavaClass permissionUtility;
	void Start () 
	{
		var jc=new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		var jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
		permissionUtility = new AndroidJavaClass("com.putao.loadfilesync.PermissionUtility");
		permissionUtility.CallStatic("Init");


		BtnPop.onClick.AddListener(() =>
		{
			if (!permissionUtility.CallStatic<bool>("CheckHasPermission", 2))
			{
				permissionUtility.CallStatic("ShowAlertWithConfirmCancel", "", "咋回事啊，也不允许让我用你的相机？!!", "去设置", "别取消昂");
			}
		});
		BtnCalendar.onClick.AddListener(() => RequestPermission(0));
		BtnCamera.onClick.AddListener(() => RequestPermission(2));
		BtnMicrophone.onClick.AddListener(() => RequestPermission(3));
		BtnLoaction.onClick.AddListener(() => RequestPermission(6));
		BtnCorseLocation.onClick.AddListener(() => RequestPermission(7));
		BtnStorage.onClick.AddListener(() => RequestPermission(8));
		BtnBluetooth.onClick.AddListener(() => RequestPermission(9));
	}

	public void RequestPermission(int type)
	{
		var result = permissionUtility.CallStatic<bool>("CheckHasPermission", type);
		Debug.Log("====>>>当前结果:" + result);
		permissionUtility.CallStatic("RequestUserPermission", type);
	}

	public void OnSystemAlertConfirmClick()
	{
		Debug.Log("===><<><点确认了吗？？？");
		permissionUtility.CallStatic("OpenSetting", 0);
	}

	public void OnRequestPermissionResult(string arg)
	{
		Debug.Log("===>>请求结果:" + arg);
	}

	public void OnPermissionAlwayeDenyed(string arg)
	{
		Debug.Log("====>>权限被始终禁止了");
		permissionUtility.CallStatic("ShowAlertWithConfirmCancel", "权限始终禁止", string.Format("咋回事啊，为啥不让我用{0}啊", GetDespByType(int.Parse(arg))), "去设置吧", "取消昂");
	}

	private string GetDespByType(int type)
	{
		switch (type)
		{
			case 0:
				return "日历";
			case 2:
				return "相机";
			case 3:
				return "麦克风";
			case 6:
				return "精确位置";
			case 7:
				return "粗略位置";
			case 8:
				return "存储";
			case 9:
				return "蓝牙";
		}
		return string.Empty;
	}
}

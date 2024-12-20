package com.tsnsched.core.components;
//TSNsched uses the Z3 theorem solver to generate traffic schedules for Time Sensitive Networking (TSN)
//
//    TSNsched is licensed under the GNU GPL version 2 or later.
//    
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <https://www.gnu.org/licenses/>.


import java.util.*;

import com.microsoft.z3.*;
import com.tsnsched.core.network.Network;

/**
 * [Class]: FlowFragment
 * [Usage]: This class is used to represent a fragment of a flow.
 * Simply put, a flow fragment is represents the flow it belongs to
 * regarding a specific switch in the path. With this approach,
 * a flow, regardless of its type, can be broken into flow fragments
 * and distributed to the switches in the network. It holds the time
 * values of the departure time, arrival time and scheduled time of
 * packets from this flow on the switch it belongs to.
 *
 */
public class FlowFragment extends Flow {
    private Boolean isModifiedOrCreated = false;
    private static final long serialVersionUID = 1L;
    private Flow parent;
    private transient RealExpr packetSizeZ3;
    private transient RealExpr packetPeriodicityZ3;
    private transient ArrayList<RealExpr> departureTimeZ3 = new ArrayList<RealExpr>();
    private transient ArrayList<RealExpr> scheduledTimeZ3 = new ArrayList<RealExpr>();
    private transient IntExpr fragmentPriorityZ3;

    private Port port;

    private PathNode referenceToNode;

    private int fragmentPriority;
    private String nodeName;
    private String nextHopName;
    private int numOfPacketsSent = Network.PACKETUPPERBOUNDRANGE;

    //TODO: CREATE REFERENCES TO PREVIOUS AND NEXT FRAGMENTS
    private FlowFragment previousFragment;
    private List<FlowFragment> nextFragments;

    private ArrayList<Double> departureTime = new ArrayList<Double>();
    private ArrayList<Double> arrivalTime = new ArrayList<Double>();
    private ArrayList<Double> scheduledTime = new ArrayList<Double>();

    /**
     * [Method]: FlowFragment
     * [Usage]: Overloaded constructor method of this class. Receives a
     * flow as a parameter so it can retrieve properties of the flows
     * that it belongs to.
     *
     * @param parent    Flow object to whom this fragment belongs to
     */
    public FlowFragment(Flow parent) {
        this.setParent(parent);

        /*
         * Every time this constructor is called, the parent is also called
         * making instanceCounter++, even though it counts only the number
         * of flows.
         */
        // Flow.instanceCounter--;

        this.nextFragments = new ArrayList<FlowFragment>();

        /*
         * Since the pathing methods for unicast and publish subscribe flows
         * are different and the size of the path makes a difference on the name,
         * there must be a type check before assigning the names
         */

        if(parent.getType() == Flow.UNICAST) {
            this.name = parent.getName() + "Fragment" + (parent.getFlowFragments().size() + 1);
        } else if (parent.getType() == Flow.PUBLISH_SUBSCRIBE) {
            this.name = parent.getName() + "Fragment" + (parent.pathTreeCount + 1);
            parent.pathTreeCount++;
        } else {
            // Throw error
        }

    }

    /*
     * GETTERS AND SETTERS
     */

    public String getName() {
        return this.name;
    }

    public RealExpr getDepartureTimeZ3(int index) {
        return departureTimeZ3.get(index);
    }

    public void setDepartureTimeZ3(RealExpr dTimeZ3, int index) {
        this.departureTimeZ3.set(index, dTimeZ3);
    }

    public void addDepartureTimeZ3(RealExpr dTimeZ3) {
        this.departureTimeZ3.add(dTimeZ3);
    }

    public void createNewDepartureTimeZ3List() {
        this.departureTimeZ3 = new ArrayList<RealExpr>();
    }

    public RealExpr getPacketPeriodicityZ3() {
        return packetPeriodicityZ3;
    }

    public void setPacketPeriodicityZ3(RealExpr packetPeriodicity) {
        this.packetPeriodicityZ3 = packetPeriodicity;
    }


    public RealExpr getPacketSizeZ3() {
        return this.packetSizeZ3;
    }

    public void setPacketSizeZ3(RealExpr packetSize) {
        this.packetSizeZ3 = packetSize;
    }

    public IntExpr getFragmentPriorityZ3() {
        return fragmentPriorityZ3;
    }

	public void setFragmentPriorityZ3(IntExpr flowPriority) {
        this.fragmentPriorityZ3 = flowPriority;
    }


    public void addDepartureTime(double val) {
        departureTime.add(val);
    }

    public double getDepartureTime(int index) {
        return departureTime.get(index);
    }

    public ArrayList<Double> getDepartureTimeList() {
        return departureTime;
    }

    public void addArrivalTime(double val) {
        arrivalTime.add(val);
    }

    public double getArrivalTime(int index) {
        return arrivalTime.get(index);
    }

    public ArrayList<Double> getArrivalTimeList() {
        return arrivalTime;
    }

    public void addScheduledTime(double val) {
        scheduledTime.add(val);
    }

    public double getScheduledTime(int index) {
        return scheduledTime.get(index);
    }

    public ArrayList<Double> getScheduledTimeList() {
        return scheduledTime;
    }

    public String getNextHop() {
        return nextHopName;
    }

    public void setNextHop(String nextHop) {
        this.nextHopName = nextHop;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Override
    public int getNumOfPacketsSent() {
        return numOfPacketsSent;
    }

    @Override
    public void setNumOfPacketsSent(int numOfPacketsSent) {
        this.numOfPacketsSent = numOfPacketsSent;
    }

    public Flow getParent() {
        return parent;
    }

    public void setParent(Flow parent) {
        this.parent = parent;
    }

    public int getFragmentPriority() {
        return fragmentPriority;
    }

    public void setFragmentPriority(int fragmentPriority) {
        this.fragmentPriority = fragmentPriority;
    }

    public FlowFragment getPreviousFragment() {
        return previousFragment;
    }

    public void setPreviousFragment(FlowFragment previousFragment) {
        this.previousFragment = previousFragment;
    }

    public List<FlowFragment> getNextFragments() {
        return nextFragments;
    }

    public void setNextFragments(List<FlowFragment> nextFragments) {
        this.nextFragments = nextFragments;
    }

    public void addToNextFragments(FlowFragment frag) {
        this.nextFragments.add(frag);
    }

    public PathNode getReferenceToNode() {
        return referenceToNode;
    }

    public void setReferenceToNode(PathNode referenceToNode) {
        this.referenceToNode = referenceToNode;
    }

    public Boolean getIsModifiedOrCreated() {
        return isModifiedOrCreated;
    }

    public void setIsModifiedOrCreated(Boolean isModifiedOrCreated) {
        this.isModifiedOrCreated = isModifiedOrCreated;
    }

    public Port getPort() {
        return this.port;
    }

    public RealExpr getScheduledTimeZ3(int index) {
        return scheduledTimeZ3.get(index);
    }

    public void setScheduledTimeZ3(RealExpr sTimeZ3, int index) {
        this.scheduledTimeZ3.set(index, sTimeZ3);
    }

    public void addScheduledTimeZ3(RealExpr sTimeZ3) {
        this.scheduledTimeZ3.add(sTimeZ3);
    }

    public void createNewScheduledTimeZ3List() {
        this.scheduledTimeZ3 = new ArrayList<RealExpr>();
    }

    public void setPort(Port port) {
        this.port = port;
    }

	public int getSizeOfDepartureTimeListZ3() {
		return this.departureTimeZ3.size();
	}

    public void resetOutputLists() {
        this.departureTime = new ArrayList<Double>();
        this.arrivalTime = new ArrayList<Double>();
        this.scheduledTime = new ArrayList<Double>();
    }

    public double getSizeOfDepartureTimeList() {
        return this.departureTime.size();
    }
}

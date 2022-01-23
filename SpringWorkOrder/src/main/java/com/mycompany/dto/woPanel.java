/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dto;

import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author Kubus
 */
public class woPanel{

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWoType() {
        return woType;
    }

    public void setWoType(String woType) {
        this.woType = woType;
    }
    
    public static Comparator<woPanel> woNumberComparatorASC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    String wo1Number = wo1.getWoNumber();
                    String wo2Number = wo2.getWoNumber();
                    
                    return wo1Number.compareTo(wo2Number);
                }
            };
    
    public static Comparator<woPanel> woNumberComparatorDESC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    String wo1Number = wo1.getWoNumber();
                    String wo2Number = wo2.getWoNumber();
                    
                    return wo2Number.compareTo(wo1Number);
                }
            };
    
    public static Comparator<woPanel> woPlanningStartComparatorASC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    Date wo1Date = wo1.getPlanningStart();
                    Date wo2Date = wo2.getPlanningStart();
                    
                    return wo1Date.compareTo(wo2Date);
                }
            };
    
    public static Comparator<woPanel> woPlanningStartComparatorDESC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    Date wo1Date = wo1.getPlanningStart();
                    Date wo2Date = wo2.getPlanningStart();
                    
                    return wo2Date.compareTo(wo1Date);
                }
            };
    
    public static Comparator<woPanel> woPlanningStopComparatorASC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    Date wo1Date = wo1.getPlanningStop();
                    Date wo2Date = wo2.getPlanningStop();
                    
                    return wo1Date.compareTo(wo2Date);
                }
            };
    
    public static Comparator<woPanel> woPlanningStopComparatorDESC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    Date wo1Date = wo1.getPlanningStop();
                    Date wo2Date = wo2.getPlanningStop();
                    
                    return wo2Date.compareTo(wo1Date);
                }
            };
    
    public static Comparator<woPanel> woStartComparatorASC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    Date wo1Date = wo1.getStart();
                    Date wo2Date = wo2.getStart();
                                        
                    return wo1Date.compareTo(wo2Date);
                }
            };
    
    public static Comparator<woPanel> woStartComparatorDESC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    Date wo1Date = wo1.getStart();
                    Date wo2Date = wo2.getStart();
                    
                    return wo2Date.compareTo(wo1Date);
                }
            };
    
    public static Comparator<woPanel> woStopComparatorASC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    Date wo1Date = wo1.getStop();
                    Date wo2Date = wo2.getStop();
                    
                    return wo1Date.compareTo(wo2Date);
                }
            };
    
    public static Comparator<woPanel> woStopComparatorDESC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    Date wo1Date = wo1.getStop();
                    Date wo2Date = wo2.getStop();
                    
                    return wo2Date.compareTo(wo1Date);
                }
            };
    
    public static Comparator<woPanel> woStatusComparatorASC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    String wo1Status = wo1.getWoStatus();
                    String wo2Status = wo2.getWoStatus();
                    
                    return wo1Status.compareTo(wo2Status);
                }
            };
    
    public static Comparator<woPanel> woStatusComparatorDESC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    String wo1Status = wo1.getWoStatus();
                    String wo2Status = wo2.getWoStatus();
                    
                    return wo2Status.compareTo(wo1Status);
                }
            };
    
    public static Comparator<woPanel> woDelayedStartComparatorASC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    boolean wo1DStart = wo1.isDelayedStart();
                    boolean wo2DStart = wo2.isDelayedStart();
                    
                    return Boolean.valueOf(wo1DStart).compareTo(wo2DStart);
                }
            };
    
    public static Comparator<woPanel> woDelayedStartComparatorDESC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    boolean wo1DStart = wo1.isDelayedStart();
                    boolean wo2DStart = wo2.isDelayedStart();
                    
                    return Boolean.valueOf(wo2DStart).compareTo(wo1DStart);
                }
            };
    
    public static Comparator<woPanel> woDelayedStopComparatorASC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    boolean wo1DStop = wo1.isDelayedStop();
                    boolean wo2DStop = wo2.isDelayedStop();
                    
                    return Boolean.valueOf(wo1DStop).compareTo(wo2DStop);
                }
            };
    
    public static Comparator<woPanel> woDelayedStopComparatorDESC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    boolean wo1DStop = wo1.isDelayedStop();
                    boolean wo2DStop = wo2.isDelayedStop();
                    
                    return Boolean.valueOf(wo2DStop).compareTo(wo1DStop);
                }
            };
    
    public static Comparator<woPanel> woSizeComparatorASC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    int wo1Size = wo1.getSize();
                    int wo2Size = wo2.getSize();
                    
                    return Integer.valueOf(wo1Size).compareTo(wo2Size);
                }
            };   
    
    public static Comparator<woPanel> woSizeComparatorDESC
            = new Comparator<woPanel>() {
                
                @Override
                public int compare(woPanel wo1, woPanel wo2) {
                    //asc
                    int wo1Size = wo1.getSize();
                    int wo2Size = wo2.getSize();
                    
                    return Integer.valueOf(wo2Size).compareTo(wo1Size);
                }
            };    
    
    
    
    
    public String getWoNumber() {
        return woNumber;
    }

    public void setWoNumber(String woNumber) {
        this.woNumber = woNumber;
    }

    public Date getPlanningStart() {
        return planningStart;
    }

    public void setPlanningStart(Date planningStart) {
        this.planningStart = planningStart;
    }

    public Date getPlanningStop() {
        return planningStop;
    }

    public void setPlanningStop(Date planningStop) {
        this.planningStop = planningStop;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public String getWoStatus() {
        return woStatus;
    }

    public void setWoStatus(String woStatus) {
        this.woStatus = woStatus;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isDelayedStop() {
        return delayedStop;
    }

    public void setDelayedStop(boolean delayedStop) {
        this.delayedStop = delayedStop;
    }

    public boolean isDelayedStart() {
        return delayedStart;
    }

    public void setDelayedStart(boolean delayedStart) {
        this.delayedStart = delayedStart;
    }
    
    private String woNumber;
    private String woType;
    private String description;
    private String areaName;
    private Date planningStart;
    private Date planningStop;
    private Date start;
    private Date stop;
    private String woStatus;
    private int size;
    private boolean delayedStop;
    private boolean delayedStart;
}
